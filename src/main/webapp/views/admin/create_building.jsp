<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Trang chủ</title>
</head>
<body>
	<div class="main-content">
		<div class="main-content-inner">
			<div class="breadcrumbs ace-save-state" id="breadcrumbs">
				<ul class="breadcrumb">
					<li><i class="ace-icon fa fa-home home-icon"></i> <a href="#">Trang
							chủ</a></li>
					<li><a href="#">Thêm tòa nhà</a></li>
				</ul>
				<!-- /.breadcrumb -->
			</div>
			<div class="page-content">
				<div class="page-header">
					<h1>
						Thêm tòa nhà <small> <i
							class="ace-icon fa fa-angle-double-right"></i> Bạn có thể thêm
							mới một tòa nhà
						</small>
					</h1>
				</div>
				<!-- /.page-header -->
				<div class="row">
					<div class="col-xs-12">
						<!-- PAGE CONTENT BEGINS -->
						<form class="form-horizontal" role="form">
							<div class="form-group">
								<div class="col-xs-6">
									<label for="form-field-1"><b>Tên sản phẩm</b></label> <input
										class="form-control" type="text" id="form-field-1" />
								</div>
								<div class="col-xs-6">
									<label for="form-field-2"><b>Diện tích sàn</b></label> <input
										class="form-control" type="text" id="form-field-2" />
								</div>
							</div>
							<div class="form-group">
								<div class="col-xs-4">
									<label for="form-field-3"><b>Quận hiện có</b></label> <select
										class="form-control" id="form-field-3">
										<option value="">Chọn quận</option>
										<option value="Quan 1">Quan 1</option>
										<option value="Quan 2">Quan 2</option>
										<option value="Quan 3">Quan 3</option>
										<option value="Quan 4">Quan 4</option>
										<option value="Quan 5">Quan 5</option>
										<option value="Quan 6">Quan 6</option>
										<option value="Quan 7">Quan 7</option>
										<option value="Quan 8">Quan 8</option>
										<option value="Quan 9">Quan 9</option>
										<option value="Quan 10">Quan 10</option>
										<option value="Quan 11">Quan 11</option>
										<option value="Quan 12">Quan 12</option>
									</select>
								</div>
								<div class="col-xs-4">
									<label for="form-field-4"><b>Phường</b></label> <input
										class="form-control" type="text" id="form-field-4" />
								</div>
								<div class="col-xs-4">
									<label for="form-field-5"><b>Đường</b></label> <input
										class="form-control" type="text" id="form-field-5" />
								</div>
							</div>
							<div class="form-group">
								<div class="col-xs-4">
									<label for="form-field-8"><b>Kết cấu</b></label> <input
										class="form-control" type="text" id="form-field-8" />
								</div>
								<div class="col-xs-4">
									<label for="form-field-6"><b>Số tầng hầm</b></label> <input
										class="form-control" type="text" id="form-field-6" />
								</div>
								<div class="col-xs-4">
									<label for="form-field-7"><b>Diện tích</b></label> <input
										class="form-control" type="text" id="form-field-7" />
								</div>
							</div>

							<div class="form-group">
								<div class="col-xs-3">
									<label for="form-field-9"><b>Giá thuê</b></label> <input
										class="form-control" type="text" id="form-field-9" />
								</div>
								<div class="col-xs-3">
									<label for="form-field-10"><b>Giá dịch vụ</b></label> <input
										class="form-control" type="text" id="form-field-10" />
								</div>
								<div class="col-xs-3">
									<label for="form-field-11"><b>Giá giữ ô tô</b></label> <input
										class="form-control" type="text" id="form-field-11" />
								</div>
								<div class="col-xs-3">
									<label for="form-field-12"><b>Giá giữ xe máy</b></label> <input
										class="form-control" type="text" id="form-field-12" />
								</div>
							</div>

							<div class="form-group">
								<div class="col-xs-4">
									<label for="form-field-9"><b>Phí ngoài giờ</b></label> <input
										class="form-control" type="text" id="form-field-9" />
								</div>
								<div class="col-xs-4">
									<label for="form-field-10"><b>Giá điện</b></label> <input
										class="form-control" type="text" id="form-field-10" />
								</div>
								<div class="col-xs-4">
									<label for="form-field-11"><b>Đặt cọc</b></label> <input
										class="form-control" type="text" id="form-field-11" />
								</div>
							</div>
							<div class="form-group">
								<div class="col-xs-4">
									<label for="form-field-9"><b>Thanh toán</b></label> <input
										class="form-control" type="text" id="form-field-9" />
								</div>
								<div class="col-xs-4">
									<label for="form-field-10"><b>Thời gian thuê</b></label> <input
										class="form-control" type="text" id="form-field-10" />
								</div>
								<div class="col-xs-4">
									<label for="form-field-11"><b>Tên quản lý</b></label> <input
										class="form-control" type="text" id="form-field-11" />
								</div>
							</div>
							<div class="form-group">
								<div class="col-xs-4">
									<label for="form-field-9"><b>Điện thoại quản lý</b></label> <input
										class="form-control" type="text" id="form-field-9" />
								</div>
								<div class="col-xs-4">
									<label for="form-field-10"><b>Thời gian thuê</b></label> <input
										class="form-control" type="text" id="form-field-10" />
								</div>
								<div class="col-xs-4">
									<label for="form-field-11"><b>Tên quản lý</b></label> <input
										class="form-control" type="text" id="form-field-11" />
								</div>
							</div>
							<div class="form-group">
								<div class="col-xs-4">
									<label>Upload Image</label>
									<div class="input-group">
										<span class="input-group-btn"> <span
											class="btn btn-default btn-file"> Browse… <input
												type="file" id="imgInp">
										</span>
									</div>
								</div>
							</div>
							<div class="form-group">
								<button type="button" class="btn btn-sm btn-success">
									<b>Thêm tòa nhà</b> <i
										class="ace-icon fa fa-arrow-right icon-on-right bigger-110"></i>
								</button>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- /.main-content -->
</body>
</html>