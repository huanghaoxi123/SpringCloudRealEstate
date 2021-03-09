<!DOCTYPE html>

<html lang="en-US">
<@common.header/>

<body class="page-sub-page page-create-account page-account" id="page-top">
<!-- Wrapper -->
<div class="wrapper">
    <!-- Navigation -->
    <@common.nav/>
    <!-- end Navigation -->
    <!-- Page Content -->
    <div id="page-content">
        <!-- Breadcrumb -->
        <div class="container">
            <ol class="breadcrumb">
                <li><a href="#">Home</a></li>
                <li class="active">Reset Password</li>
            </ol>
        </div>
        <!-- end Breadcrumb -->

        <div class="container">
             <header><h1>${email!} Reset Password</h1></header>
            <div class="row">
                <div class="col-md-4 col-sm-6 col-md-offset-4 col-sm-offset-3">
                    <form role="form" id="form-create-account" method="post" action="/accounts/resetSubmit" >
                       
                        <div class="form-group">
                            <label for="form-create-account-password">Password:</label>
                            <input type="password" name="passwd" class="form-control" id="form-create-account-password" minlength="6" required>
                            <span id='message'></span>
                        </div><!-- /.form-group -->

                         <div class="form-group">
                            <label for="form-create-account-confirm-password">Confirm Password:</label>
                            <input type="password" name="confirmPasswd" class="form-control" id="form-create-account-confirm-password" required>
                        </div>
                        <input type="hidden"  name="email" value="${email!}">
                        <input type="hidden"  name="key" value="${success_key!}">
                        <div class="form-group clearfix">
                            <button type="submit" class="btn pull-right btn-default" id="account-submit">Confirm</button>
                        </div><!-- /.form-group -->
                    </form>
                    <hr>
                    
                </div>
            </div>
           
        </div><!-- /.container -->
    </div>
    <!-- end Page Content -->
    <!-- Page Footer -->
    <@common.footer/>
    <!-- end Page Footer -->
</div>

<@common.js/>
<!--[if gt IE 8]>
<script type="text/javascript" src="/assets/js/ie.js"></script>
<![endif]-->
 <script  type="text/javascript" >
    $(document).ready(function() {
          var errorMsg   = "${errorMsg!""}";
          var successMsg = "${successMsg!""}";
          if(errorMsg){ 
              errormsg("error",errorMsg);
          }
          if(successMsg) {
              successmsg("success",successMsg);
          }

    //check the password to be same
    $('#form-create-account-password, #form-create-account-confirm-password').on('keyup', function () {
          if ($('#form-create-account-password').val() == $('#form-create-account-confirm-password').val()) {
            $('#message').html('');
          } else 
            $('#message').html('Password Not Matching').css('color', 'red');
        });


        })


    var password = document.getElementById("form-create-account-password")
      , confirm_password = document.getElementById("form-create-account-confirm-password");

    function validatePassword(){
      if(password.value != confirm_password.value) {
        confirm_password.setCustomValidity("Passwords Don't Match");
      } else {
        confirm_password.setCustomValidity('');
      }
    }

    password.onchange = validatePassword;
    confirm_password.onkeyup = validatePassword;
        
 </script>
</body>
</html>