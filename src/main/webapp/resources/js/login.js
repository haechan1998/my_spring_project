console.log("login page in");
// console.log(attemptsMessage);
console.log(errorMessage);
console.log(userId);
const messageTit = document.getElementById('message-tit');

if(errorMessage != null){
    messageTit.innerText = errorMessage;
}

const loginInputId = document.getElementById('loginInputId');

const loginInputPassword = document.getElementById('loginInputPassword');

const loginSubBtn = document.getElementById('loginSubBtn');



loginSubBtn.addEventListener('click', () => {

    
    if(loginInputPassword.value == ''){
        alert("비밀번호를 입력해주세요")
        return;
    }
})
