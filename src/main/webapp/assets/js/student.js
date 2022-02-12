$(function(){
    $(".main_menu a:nth-child(4)").addClass("active");

    $("#search_dep").click(function(){
        $(".department_search").css("display", "block");
    })

    $("#dep_search_close").click(function(){
        $(".department_search").css("display", "");
    })

    $("#dep_keyword").keyup(function(event){
        if(event.keyCode == 13) $("#dep_search_btn").trigger("click");
    })

    $("#dep_search_btn").click(function(){
        $.ajax({
            url:"/department/keyword?keyword="+$("#dep_keyword").val(),
            type:"get",
            success:function(result) {
                console.log(result);
                $(".search_result ul").html("");
                for(let i=0; i<result.list.length; i++) {
                    let str_status = "";
                    if(result.list[i].di_status == 1) str_status = "운영중"
                    if(result.list[i].di_status == 2) str_status = "통합예정"
                    if(result.list[i].di_status == 3) str_status = "폐지예정"
                    if(result.list[i].di_status == 4) str_status = "폐지"
                let tag = 
                        '<li>'+
                            '<a href="#" data-dep-seq="'+result.list[i].di_seq+'">'+result.list[i].di_name+'</a>'+
                            '<span class="status'+result.list[i].di_status+'">'+str_status+'</span>'+
                        '</li>';
                        $(".search_result ul").append(tag);
                }

                $(".search_result ul a").click(function(event){
                    event.preventDefault();
                    let seq = $(this).attr("data-dep-seq");
                    let name = $(this).html();

                    $("#student_dep_name").attr("data-dep-seq", seq);
                    $("#student_dep_name").val(name);
                    $(".search_result ul").html("");
                    $("#dep_keyword").val("");
                    $(".department_search").css("display", "")
                })
            }
        })
    })
    $("#save").click(function(){
        
        if($("#student_dep_name").attr("data-dep-seq") == undefined) {
            alert("학과를 입력해 주세요.");
            return;
        }

        if($("#student_pwd").val() != $("#student_pwd_confirm").val()) {
            alert("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
            return;
        }

        if($("#student_pwd").val() == "") {
            alert("비밀번호를 입력해 주세요.");
            return;
        }

        let data = {
            si_di_seq:$("#student_dep_name").attr("data-dep-seq"),
            si_name:$("#student_name").val(),
            si_number:$("#student_number").val(),
            si_pwd:$("#student_pwd").val(),
            si_birth:$("#student_birth").val(),
            si_phone_num:$("#student_phone_num").val(),
            si_email:$("#student_email").val(),
            si_status:$("#student_status option:selected").val()
        }

        console.log(JSON.stringify(data));

        $.ajax({
            url:"/student/add",
            type:"post",
            data:JSON.stringify(data),
            contentType:"application/json",
            success:function(result){
                alert(result.message);
                if(result.status)
                location.reload();
            }
        })
    })

    $("#add_department").click(function(){
        $(".popup_wrap").css("display", "block");
        $("#save").css("display", "inline-block");
        $("#update").css("display", "none");
        $("#cancel").css("display", "inline-block");
        $(".top_area h2").html("학생 추가")
        $(".top_area p").html("학생 정보를 입력해 주세요.");
        
    })

    $("#cancel").click(function(){
        $("#student_dep_name").attr("data-dep-seq",""),
        $("#student_dep_name").val("학과 명"),
        $("#student_name").val(""),
        $("#student_number").val(""),
        $("#student_pwd").val(""),
        $("#student_birth").val(""),
        $("#student_phone_num").val(""),
        $("#student_email").val(""),
        $("#student_status").val(1).prop("selected", true);

        $(".popup_wrap").css("display", "none");
    })
})