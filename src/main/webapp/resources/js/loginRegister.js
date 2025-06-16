console.log("login register in");

const id = document.getElementById('isDuplicateId');
const email = document.getElementById('isDuplicateEmail');
const nick = document.getElementById('isDuplicateNickName');
const phone = document.getElementById('isDuplicatePhone');
const password = document.getElementById('loginRegisterPassword');
const userName = document.getElementById('loginRegisterName');

const checkId = document.getElementById('checkId');
const checkNick = document.getElementById('checkNick');

const regBtn =document.getElementById('registerSubBtn');

let isCheckedId = false;
let isCheckedNick = false;



// 중복체크 라인 ------------------------------------------------------------------------
// 모든 input 태그에 이벤트를 걸어서 빈값이 있는지 체크
const inputs = [
    id,
    email,
    nick,
    phone,
    userName
];

inputs.forEach(input => {
    
    input.addEventListener('input', () => {
        if(inputs.value == ""){
            regBtn.disabled = true;
        }

        isCheckeElement(isCheckedId, isCheckedNick);
       
    })
    
})

function isCheckeElement(isCheckedId, isCheckedNick){
    if((isCheckedId && isCheckedNick)){
        regBtn.disabled = false;
    }else{
        regBtn.disabled = true;
    }
}

// 중복체크만 하고 아이디, 닉네임을 변경 할 경우를 방지
const isDupliCheckElement = [
    id,
    nick
];

isDupliCheckElement.forEach(e => {
    e.addEventListener('input', () => {
        regBtn.disabled = true;
    })
})

// 기존의 아이디만 방지한경우
// document.getElementById('isDuplicateId').addEventListener('input', () => {
//     regBtn.disabled = true;
// })

// 입력이 안되면 애초에 가입 버튼이 비활성화... 그래서 주석
// regBtn.addEventListener('click', () => {
//     if (!validateInputs()) return;
// })

// 아이디 중복체크
document.getElementById("isIdDuplicateBtn").addEventListener('click', () => {
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
    
    postIdLoginInfoToServer(loginData).then(result => {
        if(result == 'ok_id'){
            checkId.innerText = "사용 가능한 아이디입니다.";
            isCheckedId = true;
            
            
        }else if(result == 'fail_id'){
            checkId.innerText = "중복된 아이디입니다.";
            isCheckedId = false;
            id.focus();
            
        }
        isCheckeElement(isCheckedId, isCheckedNick);

    })
})

// 닉네임 중복체크
document.getElementById("isNickDuplicateBtn").addEventListener('click', () => {
    if(nick.value === ''){
        alert("닉네임를 입력해주세요");
        nick.focus();
        return;
    }
    
    let loginData = {
        userId : id.value,
        email : email.value,
        nickName : nick.value,
        phone : phone.value
    }
    
    postNickLoginInfoToServer(loginData).then(result => {
        if(result == 'ok_nick'){
            checkNick.innerText = "사용 가능한 닉네임입니다.";
            isCheckedNick = true;
            
        }else if(result == 'fail_nick'){
            checkNick.innerText = "중복된 닉네임입니다.";
            isCheckedNick = false;
            nick.focus();
            
        }
        isCheckeElement(isCheckedId, isCheckedNick);
    })
})


// 비동기로 register id input 값 보내기
async function postIdLoginInfoToServer(loginData) {
    try {
        const url = "/user/isDuplicateId"
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
// 비동기로 register nick input 값 보내기
async function postNickLoginInfoToServer(loginData) {
    try {
        const url = "/user/isDuplicateNick"
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

// input이 입력이 안되면 가입이 비활성화... 그래서 주석
// function validateInputs() {
//     if(email.value === '') {
//         alert("이메일을 입력해주세요");
//         email.focus();
//         return false;
//     }
//     if(phone.value === '') {
//         alert("전화번호를 입력해주세요");
//         phone.focus();
//         return false;
//     }
//     if(password.value === '') {
//         alert("비밀번호를 입력해주세요");
//         password.focus();
//         return false;
//     }
//     if(userName.value === '') {
//         alert("이름을 입력해주세요");
//         userName.focus();
//         return false;
//     }
//     return true;
// }

// 중복체크 라인 ------------------------------------------------------------------------

document.getElementById('logoutLick').addEventListener('click', (e) => {
    e.preventDefault
    document.getElementById('looutForm').submit();
})