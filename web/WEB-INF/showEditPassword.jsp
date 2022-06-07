

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<h1 class="w-100 text-center my-5">Изменение пароля</h1>
<div class="w-100 d-flex justify-content-center">
    <div class="card border-0 p-5 m-4" style="width: 30rem;">
            <form action="editPassword" method="POST">
                <input hidden type="text" name="id" value="${id}" class="text-muted mb-0">
            <div class="mb-3">
                <label for="password1" class="form-label">Новый пароль</label>
                <input type="text" class="form-control"  name="password1" id="password1" placeholder="">
            </div>
            <div class="mb-3">
                <label for="password2" class="form-label">Повторите пароль</label>
                <input type="text" class="form-control"  name="password2" id="password2" placeholder="">
            </div>
              <div class="mb-3">
                <button type="submit" class="btn btn-primary">Сохранить изменения</button>
              </div>
            </form>
    </div>
</div>