<%@page contentType="text/html" pageEncoding="UTF-8"%>
<h1 class="w-100 text-center my-5">Изменение роли пользователей</h1>
<div class="w-100 d-flex justify-content-center">
    <div class="card border-0 p-5 m-4" style="width: 30rem;">
            <form action="changeRole" method="POST">
              <div class="mb-3">
                <label for="persons" class="form-label">Пользователи</label>
                <select name="personId" id="userId" class="form-select">
                    <c:forEach var="entry" items="${mapPersons}">
                        <option value="${entry.key.id}">
                            ${entry.key.user.name} ${entry.key.user.surname}. ${entry.key.login}. ${entry.value}
                        </option>
                    </c:forEach>
                </select>
                <label for="roleId" class="form-label">Роли</label>
                <select name="roleId" id="roleId" class="form-select">
                    <c:forEach var="role" items="${roles}">
                        <option value="${role.id}">${role.roleName}</option>
                    </c:forEach>
                </select>
              </div>
            <input class="btn btn-primary mt-3" type="submit" value="Изменить роль">
            </form>
    </div>
</div>