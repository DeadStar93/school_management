// department.js

$(function() {
// 추가
    $(".main_menu a:nth-child(2)").addClass("active")
    $("#add_department").click(function() {
        $(".popup_wrap").addClass("open");
        $("#add_dep").css("display", "inline-block");
        $("#modify_dep").css("display", "none");
        $(".popup .top_area h2").html("학과 추가");
        $(".popup .top_area p").html("학과 정보를 입력해 주세요.");
    })

// 등록
    $("#add_dep").click(function(){
        if(confirm("학과를 등록하시겠습니까?") == false) return;
        let dep_name = $("#dep_name").val();
        let dep_score = $("#dep_score").val();
        let dep_status = $("#dep_status option:selected").val();

        let data = {
            di_name:dep_name,
            di_graduate_score:dep_score,
            di_status:dep_status
        }

        $.ajax({
            type:"post",
            url:"/department/add",
            data:JSON.stringify(data),
            contentType:"application/json",
            success:function(result) {
                alert(result.message);
                if(result.status)
                location.reload();
            }
        })
    });

    // 취소
    $("#cancel_dep").click(function(){
        if(confirm("취소하시겠습니까?\n(입력된 정보는 저장되지 않습니다.)") == false) return;
        $("#dep_name").val("");
        $("#dep_score").val("");
        $("#dep_status").val("1").prop("selected",true);

        $(".popup_wrap").removeClass("open");
    });

// 삭제
    $(".delete_btn").click(function(){
        if(confirm("학과를 삭제하시겠습니까?\n(이 동작은 되돌릴 수 업습니다.)") == false) return;
        let seq = $(this).attr("data-seq");

        $.ajax({
            url:"/department/delete?seq="+seq,
            type:"delete",
            success:function(result) {
                alert(result.message);
                location.reload();
            }
        })
    });

    // 수정값 불러오기
//  클래스=.  id=#
    let modify_data_seq = 0;
    $(".modify_btn").click(function(){
        // alert($(this).attr("data-seq"))  확인용
        modify_data_seq = $(this).attr("data-seq");
        $(".popup_wrap").addClass("open");
        $("#add_dep").css("display", "none");
        $("#modify_dep").css("display", "inline-block");
        $(".popup .top_area h2").html("학과 수정");
        $(".popup .top_area p").html("수정할 내용을 입력해 주세요.");
        $.ajax({
            type:"get",
            url:"/department/get?seq="+$(this).attr("data-seq"),
            success:function(result) {
                console.log(result);
                $("#dep_name").val(result.data.di_name);
                $("#dep_score").val(result.data.di_graduate_score);
                $("#dep_status").val(result.data.di_status).prop("selected",true);
            }
        })  
    })

    // 수정하기
    $("#modify_dep").click(function() {
        // alert(modify_data_seq);  확인용
        if(confirm("수정하시겠습니까?") == false) return;

        let dep_name = $("#dep_name").val();
        let dep_score = $("#dep_score").val();
        let dep_status = $("#dep_status option:selected").val();

        let data = {
            di_seq:modify_data_seq,
            di_name:dep_name,
            di_graduate_score:dep_score,
            di_status:dep_status
        }

        $.ajax({
            type:"patch",
            url:"/department/update",
            data:JSON.stringify(data),
            contentType:"application/json",
            success:function(result){
                alert(result.message);
                location.reload();
            }
        })
    })

    // 검색
    $("#search_btn").click(function(){
        location.href="/department?keyword="+$("#keyword").val();
    })
    // 엔터
    $("#keyword").keydown(function(enter) {
        console.log(enter.keyCode)
        if(enter.keyCode == 13) {
            $("#search_btn").trigger("click");
        }
    })
})
