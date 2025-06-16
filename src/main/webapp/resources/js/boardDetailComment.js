/**
 * 
 */
console.log("boardDetailComment.js in");
console.log(bnoValue);

document.getElementById('cmtAddBtn').addEventListener('click', () => {

    const cmtText = document.getElementById('cmtText'); // input
    const cmtWriter = document.getElementById('cmtWriter'); // span

    if(cmtText.value == ''){
        alert('댓글을 입력해주세요');
        cmtText.focus();
        return;
    }

    let cmtData = {
        bno : bnoValue,
        writer : cmtWriter.innerText,
        content : cmtText.value
    }

    console.log(cmtData);
    postCommentToServer(cmtData).then(result => {
        
        if(result == '1'){
            alert('댓글 등록 성공');
            // 댓글 출력
            spreadCommentList(cmtData.bno);

        }
    })

    // 데이터 보내고 input 초기화..
    cmtText.value = '';
})

function spreadCommentList(bno, page=1) {
    getCommentListFromServer(bno, page).then(result => {
        console.log(result); // ph.cmtList
        console.log(result.cmtList);

        // result 출력
        const ul = document.getElementById('cmtListArea');
        if(result.cmtList.length > 0){
            if(page == 1){
                ul.innerHTML = '';
            }
            // 댓글이 있을경우
            for(let cvo of result.cmtList) {
                    let li = `<li class="list-group-item" data-cno="${cvo.cno}">`;
                        li += `<div class="mb-3">`;
                        li += `<div class="fw-bold">${cvo.writer}</div>`;
                        li += `${cvo.content}`;
                        li += `</div>`;
                        li += `<span class="badge rounded-pill text-bg-info">${cvo.regDate}</span>`;
                        if(cvo.writer == priUsername){
                            li += `<button
                            type="button"
                            class="btn btn-outline-primary mod btn-sm"
                            data-bs-toggle="modal" data-bs-target="#myModal"
                            >
                            수정
                            </button>`; // 수정버튼
                            li += `<button type="button" class="btn btn-outline-warning del btn-sm">삭제</button>`; // 삭제버튼
                        }
                        li += `</li>`;
    
                    ul.innerHTML += li;
            }
            // 더보기 버튼 숨김 여부 체크
            let moreBtn = document.getElementById('moreBtn');
            // 더보기 버튼이 표시되는 조건
            // ph => realEndPage > pageNo => 더보기 표시
            // 현재 페이지가 전체 페이지보다 작으면 표시

            if(result.pgvo.pageNo < result.realEndPage) {
                moreBtn.style.visibility = "visible";
                // 페이지 1 증가 후 다시 data-page 로 달기
                moreBtn.dataset.page = page + 1;
                
            }else{
                // 현재 페이지가 전체 페이지보다 크다면
                moreBtn.style.visibility = "hidden";
            }

        }else{
            // 댓글이 없을경우
            ul.innerHTML = `<li class="list-group-item">Comment List Empty</li>`;
        }


    })
}

document.addEventListener('click', (e) => {
    // 내가 선택한 객체 인지
    // moreBtn 을 선택 했을 때
    if(e.target.id === 'moreBtn'){
        let page = parseInt(e.target.dataset.page); // 문자로 인지 => 숫자로 변환
        spreadCommentList(bnoValue, page);
    }

    // 수정 일 경우 cno, content => 서버로 전송하여 update
    if(e.target.classList.contains('mod')){
        
        let li = e.target.closest('li');
        let cno = li.dataset.cno;
        // nextSibling : 한 부모 안에서 다음 형제 값을 찾기
        let cmtText = li.querySelector('.fw-bold').nextSibling;
        console.log(typeof cmtText);
        document.getElementById('cmtTextMod').value = cmtText.nodeValue; // nodeValue
        
        // No.cno / <b>writer</b>
        let writer = li.querySelector('.fw-bold').innerText;
        let str = ``;
            str += `No.${cno}`; // cno
            str += ` 작성자 : ${writer}`; // writer

        document.getElementById('cno-writer').innerHTML = str;

        // cmtModBtn 에 data-cno dataset 달아주기
        document.getElementById('cmtModBtn').setAttribute('data-cno', cno);

        
    }

    // Modal 수정 버튼을 클릭할 경우
    if(e.target.id === 'cmtModBtn'){

        let cmtData = {
            cno : e.target.dataset.cno,
            content : document.getElementById('cmtTextMod').value
        };

        // 비동기 보내는 함수
        updateCommentToServer(cmtData).then(result => {

            if(result == '1'){
                console.log("업데이트 성공");
            }else{
                console.log("업데이트 실패");
            }
            
            // 수정 후 댓글 출력
            spreadCommentList(bnoValue);
            
            // 모달창 닫기 : btn-close 라는 클래스를 가지는 객체를 클릭하시오.
            document.querySelector('.btn-close').click();
        })
        
        

    }

    // 삭제 일 경우
    if(e.target.classList.contains('del')){
        // 삭제 버튼이 선택된 경우
        // cno 만 있으면 됨.
        let li = e.target.closest('li'); // 내 버튼이 속해있는 li 찾기
        let cno = li.dataset.cno;

        // 비동기로 cno를 보내서 DB 상에서 삭제 isOk 리턴
        removeCommentToServer(cno, bnoValue).then(result => {
            
            if(result == '1'){
                console.log('댓글삭제 성공');

            }else{
                console.log('댓글삭제 실패');
            }
            
            
            // 댓글 출력
            spreadCommentList(bnoValue);
        })

        
    }


})

// modify
async function updateCommentToServer(cmtData) {
    try {
        const url = "/comment/update"
        const config = {
            method : 'put',
            headers : {
                'Content-Type' : 'application/json; charset=utf-8'
            },
            body : JSON.stringify(cmtData)
        }
        const resp = await fetch(url, config);
        const result = await resp.text(); // isOk 받아오기..

        return result;


    } catch (error) {
        console.log(error);
    }
}

//  delete
async function removeCommentToServer(cno,bno) {
    try {
        const url = `/comment/${cno}/${bno}`;

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


/*
    <ul class="list-group list-group-flush" id="cmtListArea">
			<li class="list-group-item">
				<div>
					<div class="fw-bold">writer</div>
					content
				</div>
				<span class="badge rounded-pill text-bg-info">regDate</span>
			</li>
		</ul>
*/

// get 
async function getCommentListFromServer(bno, page) {

    try {
        const resp = await fetch(`/comment/${bno}/${page}`); // path 로 변수 달고가기 /cmt?bno=bnoValue
        const result = await resp.json(); // List<CommnentVO> 리턴
        return result;

    } catch (error) {
        console.log(error);
    }
    
}

// post
async function postCommentToServer(cmtData) {

    try {
        const url = "/comment/post";
        const config = {
            method : "post",
            headers : {
                'Content-Type' : 'application/json; charset=utf-8'
            },
            body : JSON.stringify(cmtData)
        }
        const resp = await fetch(url, config);
        const result = await resp.text(); // isOk
        return result;

    } catch (error) {
        console.log(error);
    }

}
