<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>Sistema | Iniciar Sesi�n</title>
        <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
        <!-- Bootstrap 3.3.7 -->
        <link rel="stylesheet" href="bower_components/bootstrap/dist/css/bootstrap.min.css">
        <!-- Font Awesome -->
        <link rel="stylesheet" href="bower_components/font-awesome/css/font-awesome.min.css">
        <!-- Ionicons -->
        <link rel="stylesheet" href="bower_components/Ionicons/css/ionicons.min.css">
        <!-- Theme style -->
        <link rel="stylesheet" href="dist/css/AdminLTE.min.css">
        <!-- iCheck -->
        <link rel="stylesheet" href="plugins/iCheck/square/blue.css">
        <!-- Google Fonts -->
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,600,700,300italic,400italic,600italic">
        <!-- Custom Styles -->
        <style>
            body {
                background-color: #007fa2;
            }
            .login-box .login-logo img {
                max-width: 100%; /* Ajustar el ancho m�ximo de la imagen al 100% del contenedor */
                height: auto; /* Permitir que la altura de la imagen se ajuste autom�ticamente */
                display: block; /* Para evitar cualquier espacio adicional debajo de la imagen */
                margin: 0 auto; /* Centrar la imagen horizontalmente */
            }

        </style>
    </head>
    <body class="hold-transition login-page" style="background-color: #007fa2;">
        <!-- Contenedor principal -->
        <div class="wrapper">
            <div class="login-box">
                <div class="login-logo">
                    <img src="dist/img/logo2.jpg" alt="Logo">
                </div>
                <!-- /.login-logo -->
                <div class="login-box-body">
                    <p class="login-box-msg">Iniciar Sesi�n</p>

                    <form action="srvSession?accion=verificar" method="POST">
                        <div class="form-group has-feedback">
                            <input type="text" name="txtUsu" id="txtUsu" value="" class="form-control"  placeholder="Usuario">
                            <span class="glyphicon glyphicon-envelope form-control-feedback"></span>
                        </div>
                        <div class="form-group has-feedback">
                            <input type="password" name="txtPass" id="txtPass" value="" class="form-control" placeholder="******">
                            <span class="glyphicon glyphicon-lock form-control-feedback"></span>
                        </div>
                        <div class="row">
                            <div class="col-xs-8">
                                <div class="checkbox icheck">
                                    <label>
                                        <input type="checkbox"> Recu�rdame
                                    </label>
                                </div>
                            </div>
                            <!-- /.col -->
                            <div class="col-xs-4">
                                <input type="submit" name="verificar" value="Verificar" class="btn btn-primary btn-block"/>
                            </div>
                            <!-- /.col -->
                        </div>
                    </form>

                    <div class="social-auth-links text-center">
                        <p>- Verificaci�n Credenciales -</p>
                        <a href="#" class="btn btn-block btn-social btn-facebook btn-flat" style="background-color: #007fa2;"><i class="fa fa-info"></i> Mensaje: ${msje}</a>
                    </div>
                    <footer class="main-footer-kmi">
                        <strong>IPS PRO &copy; 2024 <a href="https://github.com/CratosCamilo" target="_blank">KMI</a>.</strong> Todos los derechos reservados.
                    </footer>

                    <!-- /.social-auth-links -->
                </div>
                <!-- /.login-box-body -->
            </div>
            <!-- /.login-box -->
        </div>
        <!-- /.wrapper -->

        <!-- Footer -->

        <!-- /.main-footer -->

        <!-- jQuery 3 -->
        <script src="bower_components/jquery/dist/jquery.min.js"></script>
        <!-- Bootstrap 3.3.7 -->
        <script src="bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
        <!-- iCheck -->
        <script src="plugins/iCheck/icheck.min.js"></script>
        <script>
            $(function () {
                $('input').iCheck({
                    checkboxClass: 'icheckbox_square-blue',
                    radioClass: 'iradio_square-blue',
                    increaseArea: '20%' /* optional */
                });
            });
        </script>
    </body>
</html>
