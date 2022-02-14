<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <%@include file="/WEB-INF/includes/header.jsp"%>
    <link rel="stylesheet" href="/assets/css/department_list.css">
    <link rel="stylesheet" href="/assets/css/student_list.css">
    <script src="/assets/js/student.js"></script>  
</head>
<body>
    <main>
        <h1><i class="fas fa-user-graduate"></i> 학생 관리</h1>
        <button id="add_department"><i class="fas fa-user-graduate"></i>학생 추가</button>
        <div class="content_area">
            <div class="menu_area">
                <div class="search_box">
                    <select id="search_type">
                        <option value="dept">학과</option>
                        <option value="name">이름</option>
                    </select>
                    <input type="text" id="keyword" placeholder="검색어 입력">
                    <button id="search_btn"><i class="fas fa-search"></i></button>
                </div>
                <button id="reset_btn">초기화</button>
            </div>
            <div class="table_area">
                <table>
                    <thead>
                        <tr>
                            <th>번호</th>
                            <th>소속</th>
                            <th>학생 번호</th>
                            <th>이름</th>
                            <th>생년월일</th>
                            <th>전화번호</th>
                            <th>이메일</th>
                            <th>상태</th>
                            <th>등록일</th>
                            <th>수정일</th>
                            <th>조작</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${data.list}" var="student">
                            <tr>
                                <td>${student.si_seq}</td>
                                <td>${student.department_name}</td>
                                <td>${student.si_number}</td>
                                <td>${student.si_name}</td>
                                <td>${student.si_birth}</td>
                                <td>${student.si_phone_num}</td>
                                <td>${student.si_email}</td>
                                <td class="student_status">
                                    <c:if test="${student.si_status == 1}">
                                        <span style="background-color: rgb(17,226,27);">정상</span>
                                    </c:if>
                                    <c:if test="${student.si_status == 2}">
                                        <span style="background-color: rgb(255,110,26);">졸업</span>
                                    </c:if>
                                    <c:if test="${student.si_status == 3}">
                                        <span style="background-color: rgb(251,186, 64);">휴학</span>
                                    </c:if>
                                    <c:if test="${student.si_status == 4}">
                                        <span style="background-color: rgb(255, 23, 23);">퇴학</span>
                                    </c:if>
                                    <c:if test="${student.si_status == 5}">
                                        <span style="background-color: rgb(14, 173, 221);">수료</span>
                                    </c:if>
                                </td>
                                <td><fmt:formatDate value="${student.si_reg_dt}" pattern="yyyy년 MM월 dd일 (EE) HH시 mm분 ss초"/></td>
                                <td><fmt:formatDate value="${student.si_mod_dt}" pattern="yyyy년 MM월 dd일 (EE) HH시 mm분 ss초"/></td>
                                <td>
                                    <button class="modify_btn" data-seq="${student.si_seq}"><i class="fas fa-pencil-alt"></i></button>
                                    <button class="delete_btn" data-seq="${student.si_seq}"><i class="fas fa-user-minus"></i></button>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
            <div class="pager_area">
                <button><i class="fas fa-arrow-left"></i></button>
                <div class="pagers">
                    <c:forEach begin="1" end="${data.page}" var="i">
                        <a href="/student?offset=${(i-1)*10}&type=${type}&keyword=${keyword}">${i}</a>  
                    </c:forEach>
                </div>
                <button><i class="fas fa-arrow-right"></i></button>
            </div>
        </div>
    </main>
    <div class="popup_wrap">
        <div class="popup" id="student_add">
            <div class="top_area">
                <div class="ico"><i class="fas fa-user-graduate"></i></div>
                <h2>학생 추가</h2>
                <p>학생 정보를 입력해 주세요.</p>
            </div>
            <div class="content_area">
                <input type="text" id="student_dep_name" placeholder="학과 명" disabled>학과
                <button id="search_dep">학과 검색</button>
                <input type="text" id="student_name" placeholder="학생 이름">
                <input type="text" id="student_number" placeholder="학생 번호(ID)">
                <input type="text" id="student_pwd" placeholder="비밀번호">
                <input type="text" id="student_pwd_confirm" placeholder="비밀번호 확인">
                <input type="text" id="student_birth" placeholder="생년월일 (YYYYMMDD)">
                <input type="text" id="student_phone_num" placeholder="전화번호 (01012345678)">
                <input type="text" id="student_email" placeholder="이메일 (mail@mail.com)">
                <select id="student_status">
                    <option value="1">재학</option>
                    <option value="2">졸업</option>
                    <option value="3">휴학</option>
                    <option value="4">퇴학</option>
                    <option value="5">수료</option>
                </select>
            </div>
            <div class="btn_area">
                <button id="save">등록하기</button>
                <button id="update">수정하기</button>
                <button id="cancel">취소하기</button>
            </div>
        </div>
    </div>
    <div class="department_search">
        <div class="dep_search_box">
            <input type="text" id="dep_keyword" placeholder="예) 학과, 컴퓨터공학, 공학">
            <button id="dep_search_btn"><i class="fas fa-search"></i></button>
        </div>
        <div class="search_result">
            <ul>
                
            </ul>
        </div>
        <div class="dep_search_buttons">
            <button id="dep_search_close">닫기</button>
        </div>
    </div>
</body>
</html>