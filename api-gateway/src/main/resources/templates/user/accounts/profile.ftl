    <!DOCTYPE html>

    <html lang="en-US">
    <@common.header/>

    <body class="page-sub-page page-profile page-account" id="page-top">
    <!-- Wrapper -->
    <div class="wrapper">
        <!-- Navigation -->
       <@common.nav/><!-- /.navigation -->
        <!-- end Navigation -->
        <!-- Page Content -->
        <div id="page-content">
            <!-- Breadcrumb -->
            <div class="container">
                <ol class="breadcrumb">
                    <li><a href="#">Home</a></li>
                    <li><a href="#">Account</a></li>
                    <li class="active">Profile</li>
                </ol>
            </div>
            <!-- end Breadcrumb -->

            <div class="container">
                <div class="row">
                <!-- sidebar -->
                <div class="col-md-3 col-sm-2">
                    <section id="sidebar">
                        <header><h3>Account</h3></header>
                        <aside>
                            <ul class="sidebar-navigation">
                                <li class="active"><a href="/accounts/profile"><i class="fa fa-user"></i><span>Profile</span></a></li>
                                <li><a href="/house/ownlist"><i class="fa fa-home"></i><span>My Properties</span></a></li>
                                <li><a href="/house/bookmarked"><i class="fa fa-heart"></i><span>Bookmarked Properties</span></a></li>
                            </ul>
                        </aside>
                    </section><!-- /#sidebar -->
                </div><!-- /.col-md-3 -->
                <!-- end Sidebar -->
                    <!-- My Properties -->
                    <div class="col-md-9 col-sm-10">
                        <section id="profile">
                            <header><h1>Profile</h1></header>
                            <div class="account-profile">
                                <div class="row">
                                    <div class="col-md-3 col-sm-3">
                                        <img alt="" class="image" src="${(loginUser.avatar)!}">
                                    </div>
                                    <div class="col-md-9 col-sm-9">
                                        <form role="form" id="form-account-profile" method="post" action="/accounts/profileSubmit">
                                            <input type="hidden" value="${(loginUser.email)!}" name="email" />
                                           
                                            <section id="contact">
                                                 <h3>Contact</h3>
                                                <dl class="contact-fields">
                                                    <dt><label for="form-account-name">Your Name:</label></dt>
                                                    <dd><div class="form-group">
                                                        <input type="text" class="form-control" id="form-account-name" name="name" required value="${(loginUser.name)!}">
                                                    </div><!-- /.form-group --></dd>
                                                    <dt><label for="form-account-phone">Phone:</label></dt>
                                                    <dd><div class="form-group">
                                                        <input type="text" class="form-control" id="form-account-phone" name="phone" value="${(loginUser.phone)!}">
                                                    </div><!-- /.form-group --></dd>
                                                    <dt><label for="form-account-email">Email:</label></dt>
                                                    <dd><div class="form-group">
                                                        <input type="text" disabled class="form-control" id="form-account-email"
                                                         name="form-account-phone"value="${(loginUser.email)!}">
                                                    </div><!-- /.form-group --></dd>
                                                  
                                                </dl>
                                            </section>
                                            <section id="about-me">
                                                <h3>About Me</h3>
                                                <div class="form-group">
                                                    <textarea class="form-control" id="form-contact-agent-message" rows="5" name="aboutme">${(loginUser.aboutme)!}</textarea>
                                                </div><!-- /.form-group -->
                                            </section>
                                             <section id="social">
                                                <div class="form-group clearfix">
                                                    <button type="submit" class="btn pull-right btn-default" id="account-submit">Update</button>
                                                </div><!-- /.form-group -->
                                            </section>

                                           
                                        </form><!-- /#form-contact -->
                                        <section id="change-password">
                                            <header><h2>Update Password</h2></header>
                                            <div class="row">
                                                <div class="col-md-6 col-sm-6">
                                                    <form role="form" id="form-account-password" method="post" action="/accounts/changePassword" >
                                                        <input type="hidden" value="${(loginUser.email)!}" name="email" />
                                                        <div class="form-group">
                                                            <label for="form-account-password-current">Current Password</label>
                                                            <input type="password" name="password" class="form-control" id="form-account-password-current" 
                                                            required>
                                                            
                                                        </div><!-- /.form-group -->
                                                        <div class="form-group">
                                                            <label for="form-account-password-new">New Password: (6 characters minimum):</label>
                                                            <input type="password" name="newPassword" class="form-control" id="form-account-password-new"
                                                            onkeyup='check();'
                                                             minlength="6" required >
                                                           <span id='message_passwd'></span>   
                                                        </div><!-- /.form-group -->
                                                        <div class="form-group">
                                                            <label for="form-account-password-confirm-new">Confirm Password</label>
                                                            <input type="password" name="confirmPassword" class="form-control" id="form-account-password-confirm-new"
                                                            required>
                                                            <span id='message'></span>
                                                        </div><!-- /.form-group -->
                                                        <div class="form-group clearfix">
                                                            <button type="submit" class="btn btn-default" id="form-account-password-submit">Update Password</button>
                                                        </div>
                                                    </form>
                                                </div>
                                                
                                            </div>
                                        </section>
                                    </div><!-- /.col-md-9 -->
                                </div><!-- /.row -->
                            </div><!-- /.account-profile -->
                        </section><!-- /#profile -->
                    </div><!-- /.col-md-9 -->
                    <!-- end My Properties -->
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
    <script type="text/javascript" src="assets/js/ie.js"></script>
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
        $('#form-account-password-confirm-new, #form-account-password-new').on('keyup', function () {
              if ($('#form-account-password-confirm-new').val() == $('#form-account-password-new').val()) {
                $('#message').html('Matching').css('color', 'green');
              } else 
                $('#message').html('Password Not Matching').css('color', 'red');
            }

            );


            })
        

    var password = document.getElementById("form-account-password-new")
      , confirm_password = document.getElementById("form-account-password-confirm-new");

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