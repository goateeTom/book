function _change() {
	$("#vCode").attr("src", "/book/user/VerifyCodeServlet?" + new Date().getTime());
}