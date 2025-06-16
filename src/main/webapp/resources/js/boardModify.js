console.log('boardModify.js in');

// remove 버튼을 클릭
document.addEventListener('click', (e) => {

    if(e.target.classList.contains('removeBtn')){
        // remove 버튼을 클릭하면 해당하는 li에 있는 파일의 uuid 값 가져오기
        let uuid = e.target.dataset.uuid;
       
        console.log(uuid);
        
        removeFileToServer(uuid).then(result => {

            if(result == '1'){  
                console.log('파일 삭제 성공');
                e.target.closest('li').remove();
            }else{
                console.log('파일 삭제 실패');
            }
            
        })
        
    }
})

// uuid 를 비동기 데이터로 삭제
async function removeFileToServer(uuid,fileType,saveDir) {
    try {

        const url = `/board/${uuid}`;
        
        const config = {
            method : 'delete'
        }

        const resp = await fetch(url, config);
        const result = await resp.text();

        return result;
        
    } catch (error) {
        console.log(error);
    }
}


