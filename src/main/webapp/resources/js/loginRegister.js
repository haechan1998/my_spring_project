console.log("login register in");

const id = document.getElementById('isDuplicateId');
const email = document.getElementById('isDuplicateEmail');
const nick = document.getElementById('isDuplicateNickName');
const phone = document.getElementById('isDuplicatePhone');
const password = document.getElementById('loginRegisterPassword');
const userName = document.getElementById('loginRegisterName');
const checkId = document.getElementById('checkId');
const regBtn =document.getElementById('registerSubBtn');
let isChecked = false;

// 중복체크만 하고 아이디를 변경 할 경우를 방지
document.getElementById('isDuplicateId').addEventListener('input', () => {
    regBtn.disabled = true;
})

regBtn.addEventListener('click', () => {
    if (!validateInputs()) return;
})

// 중복 체크 버튼
document.getElementById("idDuplicateBtn").addEventListener('click', () => {
    if(id.value === ''){
        alert("아이디를 입력해주세요");
        id.focus();
        return;
    }

    let loginData = {
        userId : id.value,
        email : email.value,
        nickName : nick.value,
        phone : phone.value
    }
    
    postLoginInfoToServer(loginData).then(result => {
        if(result == 'ok'){
            checkId.innerText = "사용 가능한 아이디입니다.";
            regBtn.disabled = false;
            isChecked = true;
            
        }else if(result == 'fail'){
            checkId.innerText = "중복된 아이디입니다.";
            regBtn.disabled = true;
            id.focus();
            isChecked = false;
            
        }
    })

    
})

// 비동기로 register input 값 보내기
async function postLoginInfoToServer(loginData) {
    try {
        const url = "/user/isDuplicate"
        const config = {
            method : "post",
            headers : {
                'Content-Type' : "application/json; charset=utf-8"
            },
            body : JSON.stringify(loginData)
        }

        const resp = await fetch(url, config);
        const result = await resp.text();
        return result;



    } catch (error) {
        console.log(error);
    }
}

function validateInputs() {
    if(email.value === '') {
        alert("이메일을 입력해주세요");
        email.focus();
        return false;
    }
    if(nick.value === '') {
        alert("닉네임을 입력해주세요");
        nick.focus();
        return false;
    }
    if(phone.value === '') {
        alert("전화번호를 입력해주세요");
        phone.focus();
        return false;
    }
    if(password.value === '') {
        alert("비밀번호를 입력해주세요");
        password.focus();
        return false;
    }
    if(userName.value === '') {
        alert("이름을 입력해주세요");
        userName.focus();
        return false;
    }
    return true;
}

