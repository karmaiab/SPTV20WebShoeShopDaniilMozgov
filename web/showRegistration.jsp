
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<h1 class="w-100 text-center my-5">Регистрация нового пользователя</h1>
<div class="w-100 d-flex justify-content-center">
    <div class="card border-0  p-5 m-4" style="width: 30rem;">
        
        <p>${info}</p>
        <form action="registration" method="POST">
            <div class="mb-3">
                <label for="firstname" class="form-label">Имя:</label>
                <input type="text" class="form-control"  name="firstname" id="firstname" placeholder="">
            </div>
            <div class="mb-3">
                <label for="lastname" class="form-label">Фамилия:</label>
                <input type="text" class="form-control"  name="lastname" id="lastname" placeholder="">
            </div>
            <div class="mb-3">
                <label for="phone" class="form-label">Телефон:</label>
                <input type="text" class="form-control"  name="phone" id="phone" placeholder="">
            </div>
            <div class="mb-3">
                <label for="login" class="form-label">Логин:</label>
                <input type="text" class="form-control"  name="login" id="login" placeholder="">
            </div>
            <div class="mb-3">
                <label for="money" class="form-label">Количество денег: </label>
                <input type="text" class="form-control"  name="money" id="money" placeholder="">
            </div>
            <div class="mb-3">
                <label for="password1" class="form-label">Пароль:</label>
                <input type="password" class="form-control"  name="password1" id="password1" placeholder="">
            </div>
            <div class="mb-3">
                <label for="password2" class="form-label">Повторить пароль: </label>
                <input type="password" class="form-control"  name="password2" id="password2" placeholder="">
            </div>
            <input class="btn btn-primary my-3" type="submit" value="Зарегистрироваться">
        </form>
    </div>
</div>