
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<h1 class="w-100 text-center my-5">Авторизация</h1>
<div class="w-100 d-flex justify-content-center">
    <form action="login" style="width: 30rem" method="POST">
      <div class="row mb-3">
        <label for="login" class="col-sm-2 col-form-label">Логин</label>
        <div class="col-sm-10">
            <input type="login" class="form-control" name="login" id="login" placeholder="">
        </div>
      </div>
      <div class="row mb-3">
        <label for="password" class="col-sm-2 col-form-label">Пароль</label>
        <div class="col-sm-10">
            <input type="password" class="form-control" name="password" id="password" placeholder="">
        </div>
      </div>
      <div class="row mb-3">
        <div class="col-sm-10 offset-sm-2">
          <div class="form-check">
            <input class="form-check-input" type="checkbox" id="gridCheck">
            <label class="form-check-label" for="gridCheck">
              Я не робот!
            </label>
          </div>
        </div>
      </div>
      <button type="submit" class="btn btn-primary">Войти</button>
      <h4 class="w-100 mt-3 text-center"><a href="showRegistration">Зарегистрироваться</a></h4>
    </form>
</div>
