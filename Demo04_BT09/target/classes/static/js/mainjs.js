// ====== CONFIG ======
const BASE_URL = ""; // cùng origin; khác cổng: "http://localhost:8005"

$(function () {
  console.log("[mainjs] loaded");

  // CLICK nút Login (không submit form)
  $("#Login").on("click", function () {
    console.log("[mainjs] login click");
    const email = $("#email").val()?.trim();
    const password = $("#password").val();

    if (!email || !password) {
      alert("Nhập email & mật khẩu");
      return;
    }

    $.ajax({
      type: "POST",
      url: BASE_URL + "/auth/login",
      dataType: "json",
      contentType: "application/json; charset=utf-8",
      data: JSON.stringify({ email, password }),
      success: function (data) {
        console.log("[mainjs] login OK");
        if (!data || !data.token) {
          alert("Không nhận được token!");
          return;
        }
        localStorage.setItem("token", data.token);
        if (data.expiresIn) {
          localStorage.setItem("tokenExpAt", String(Date.now() + Number(data.expiresIn)));
        }
        window.location.href = "/user/profile";
      },
      error: function (xhr) {
        console.error("[mainjs] login error", xhr.status, xhr.responseText);
        alert(xhr.status === 401 ? "Sai email hoặc mật khẩu" : "Login failed");
      },
    });
  });
});
