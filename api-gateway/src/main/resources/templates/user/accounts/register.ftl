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
                <li class="active">Create An Account</li>
            </ol>
        </div>
        <!-- end Breadcrumb -->

        <div class="container">
            <header><h1>Create An Account</h1></header>
            <div class="row">
                <div class="col-md-4 col-sm-6 col-md-offset-4 col-sm-offset-3">
                    <h3>Account Type</h3>
                    <form role="form" id="form-create-account" method="post" enctype="multipart/form-data" action="/accounts/register">
                        <div class="radio" id="create-account-user">
                            <label>
                                <input type="radio" value="1" id="account-type-user"  name="type" required>Regular User
                            </label>
                        </div>
                        <div class="radio" id="agent-switch" data-agent-state="">
                            <label>
                                <input type="radio" value="2" id="account-type-agent" name="type" required>Agent
                            </label>
                        </div>
                        <div id="agency" class="disabled">
                            <div class="form-group">
                                <label for="account-agency">Select your agency:</label>
                                <select name="agencyId" id="account-agency">
                                      <option value="0" >Select your agency</option>
                                     <#list agencyList as agency>
                                        <option value="${agency.id}" >${agency.name}
                                        </option>
                                     </#list>

                                </select>
                            </div><!-- /.form-group -->
                        </div>
                        <hr>
                        <div class="form-group">
                            <label for="form-create-account-full-name">Full Name:</label>
                            <input type="text" class="form-control" id="form-create-account-full-name" name="name" required>
                        </div><!-- /.form-group -->
                        <div class="form-group">
                            <label for="form-create-account-email">Email:</label>
                            <input type="email" class="form-control" id="form-create-account-email" type="email"  name="email" required>
                        </div><!-- /.form-group -->
                        <div class="form-group">
                            <label for="form-create-account-email">Phone:</label>
                            <input type="text" class="form-control" id="form-create-account-email"  name="phone" required>
                        </div><!-- /.form-group -->
                        <div class="form-group">
                            <label for="form-create-account-password">Password (6 characters minimum):</label>
                            <input type="password" placeholder="6 characters minimum" class="form-control" id="form-create-account-password" name="passwd"
                            onkeyup='check();' minlength="6" required>
                             <span id='message_passwd'></span>
                        </div><!-- /.form-group -->
                        <div class="form-group">
                            <label for="form-create-account-confirm-password">Confirm Password:</label>
                            <input type="password" class="form-control" id="form-create-account-confirm-password" name="confirmPasswd" required>
                            <span id='message'></span>
                        </div><!-- /.form-group -->
                         <div class="form-group">
                            <label for="form-create-account-email">About Me:</label>
                            <textarea class="form-control" name="aboutme"></textarea> 
                        </div>
                        <div class="form-group">
                            <label for="form-create-account-email">User Photo:</label>
                            <input id="file-upload" type="file" class="file" multiple="true" data-show-upload="false" data-show-caption="false" data-show-remove="false" accept="image/jpeg,image/png" data-browse-class="btn btn-default" data-browse-label="Browse Images" name="avatarFile" required>
                            <figure class="note"><strong>Hint:</strong> Photo can be JPG or PNG format!</figure>
                        </div>
                              
                        
                        <div class="form-group clearfix">
                            <button type="submit" class="btn pull-right btn-default" id="account-submit">Create An Account</button>
                        </div><!-- /.form-group -->
                        
                        

                    </form>
                    <hr>
                </div>
            </div><!-- /.row -->
        </div><!-- /.container -->
    </div>
    <!-- end Page Content -->
    <!-- Page Footer -->
    <@common.footer/>
    <!-- end Page Footer -->
</div>

<@common.js/>
<!--[if gt IE 8]>
<script type="text/javascript" src="/static/assets/js/ie.js"></script>
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

    $('#form-create-account-password, #form-create-account-confirm-password').on('keyup', function () {
      if ($('#form-create-account-password').val() == $('#form-create-account-confirm-password').val()) {
        $('#message').html('');
      } else 
        $('#message').html('Password Not Matching').css('color', 'red');
    }


    );
    })



    var password = document.getElementById("form-create-account-password")
      , confirm_password = document.getElementById("form-create-account-confirm-password");

 
    var check = function() {
      if (password.value.length < 6) {
        document.getElementById('message_passwd').style.color = 'red';
        document.getElementById('message_passwd').innerHTML = 'Password 6 need characters minimum';
      } 
      else{
         document.getElementById('message_passwd').innerHTML = '';
      }
    }




    password.onchange = validatePassword;
    confirm_password.onkeyup = validatePassword;




        
 </script>
</body>
</html>