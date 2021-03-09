<!DOCTYPE html>

<html lang="en-US">
<@common.header/>

<body class="page-sub-page page-agency-detail" id="page-top">
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
                <li><a href="#">Agency</a></li>
                <li class="active">Agency List</li>
            </ol>
        </div>
        <!-- end Breadcrumb -->

        <div class="container">
            <div class="row">
                <!-- Agent Detail -->
                <div class="col-md-9 col-sm-9">
                    <section id="agent-detail">
                        <header><h1>${agency.name}</h1></header>
                        <section id="agent-info">
                            <div class="row">
                                <div class="col-md-3 col-sm-3">
                                    <figure class="agency-image"><img alt="" src="/static/assets/img/agency-logo-big-01.png"></figure>
                                </div><!-- /.col-md-3 -->
                                <div class="col-md-5 col-sm-5">
                                    <h3>Contact</h3>
                                    <address>
                                        ${agency.address}
                                    </address>
                                    <dl>
                                        <dt>Phone:</dt>
                                        <dd>${agency.phone}</dd>
                    
                                        <dt>Email:</dt>
                                        <dd><a href="mailto:#">${agency.email}</a></dd>
                                        <dt>Website:</dt>
                                        <dd>${agency.webSite}</dd>
                                    </dl>
                                </div><!-- /.col-md-5 -->
                                <div class="col-md-4 col-sm-4">
                                    <h3>About us</h3>
                                    <p>${agency.aboutUs} 
                                    </p>
                                   
                                </div><!-- /.col-md-4 -->
                            </div><!-- /.row -->
                            <div class="row">

                            </div><!-- /.row -->
                        </section><!-- /#agent-info -->
                      
                    </section><!-- /#agent-detail -->
                </div><!-- /.col-md-9 -->
                <!-- end Agency Detail -->
            
        <div class="container">
            <div class="row">
                <!-- Agent Detail -->
                <div class="col-md-9 col-sm-9">
                    <section id="agents-listing">
                        <header><h1>Agent List</h1></header>
                        <div class="row">
                          <#list ps.list as agent>
                            <div class="col-md-12 col-lg-6" >
                                <div class="agent">
                                    <a href="/agency/agentDetail?id=${agent.id!}" class="agent-image"><img alt="" src="${agent.avatar}"></a>
                                    <div class="wrapper">
                                        <header><a href="/agency/agentDetail?id=${agent.id!}"><h2>${agent.name!}</h2></a></header>
                                        <dl>
                                            <dt>Phone:</dt>
                                            <dd>${agent.phone!}</dd>
                                            <dt>Email:</dt>
                                            <dd><a href="mailto:#">${agent.email!}</a></dd>
                                            <dt>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</dt>
                                            <dd>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</dd>
                                            <dt>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</dt>
                                            <dd>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</dd>
                                        </dl>
                                    </div>
                                </div><!-- /.agent -->
                            </div><!-- /.col-md-12 -->



                          </#list>
             </div><!-- /.row -->
                     


                <!-- end Sidebar -->
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
        })
        
 </script>

</body>
</html>