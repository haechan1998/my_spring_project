console.log('BoardRegister.js in');

document.getElementById('trigger').addEventListener('click', () => {
    document.getElementById('file').click();
})

// 파일에 대한 정규표현식 작성
// 1. 실행파일 업로드 금지
// 2. 20MB 파일 최대 사이즈 이상은 금지

const regExp = new RegExp("\.(exe|jar|msi|dll|sh|bat)$");
const maxSize = 1024*1024*20;


function fileValidation(fileName, fileSize){
    if(regExp.test(fileName)){
        return 0;
    }else if(fileSize > maxSize){
        return 0;
    }else{
        return 1;
    }
}

document.addEventListener('change', (e) => {
    
    if(e.target.id === 'file'){
        // type='file' element 에 저장된 file 정보를 가져오는 property => files
        const fileObj = document.getElementById('file').files;
        console.log(fileObj);

        // 내가 등록한 파일의 목록을 fileList 영역에 추가 => valid 해서 정보를 같이 표시
        const div = document.getElementById('fileList');
        div.innerHTML = ''; // 만약 새로 추가되는 목록이 있따면 제하고 재처리
        
        // 한번 disalbed 되면 혼자 false 가 될 수 없음. (버튼 원상복구)
        document.getElementById('registerBtn').disabled = false;
        
        let isOk = 1; // valid 검증을 통과했는지의 여부
        // 파일이 여러개 입력가능 => 모두 다 valid 를 통과해야 등록가능
        // 모든 결과가 1이어야 register 버튼을 활성화 => 누적곱을 통해 검증
    
        let ul = `<ul>`;
            // 개별 파일에 대한 검증 / 결과표시
            for(let file of fileObj){
                let validResult = fileValidation(file.name, file.size); // 개별결과
                isOk *= validResult; // 전체누적
                ul += `<li>`;
                ul += `<div>`;
                ul += `${validResult ? '<div">업로드 가능' : '<div">업로드 불가능'}`;
                if(file.size > maxSize){
                    ul += `<span">${(file.size/(1024*1024)).toFixed(2)}MB</span>`;
                }
                ul += `</div>`;
                ul += `${file.name}`;
                ul += `</div>`;
                ul += `<span>${file.size} Bytes</span>`;
                ul += `</li>`;
            }
            ul+= `</ul>`
    
            div.innerHTML = ul;
            
            if(isOk == 0){
                // 하나라도 검증을 통과하지 못한 파일이 있다면 regBtn 비활성화
                document.getElementById('registerBtn').disabled = true;
            }
       
    }

})