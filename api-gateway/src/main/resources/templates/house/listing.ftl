<!DOCTYPE html>

<html lang="en-US">
<@common.header/>



<body class="page-sub-page page-listing-lines page-search-results" id="page-top">
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
                <li class="active">Property List</li>
            </ol>
        </div>
        <!-- end Breadcrumb -->

                      

        <div class="container">
            <div class="row">
                <!-- Results -->
                <div class="col-md-9 col-sm-9">
                    <section id="results">
                        <header><h1>Property List</h1></header>
                        <section id="search-filter">
                            <figure><h3><i class="fa fa-search"></i>Search Result:</h3>
                                <span class="search-count"></span>
                                 <div class="sorting">
                                    <div class="form-group">
                                        <select name="sorting" id="sorting">
                                        <option value="">Sort by</option>
                                        <option value="price_asc"   <#if (vo.sort) == "price_asc">   selected </#if>  >Price low to high</option>
                                        <option value="price_desc"  <#if (vo.sort) == "price_desc">  selected </#if> >price high to low</option>
                                           <!--  <option value="time_desc"   <#if (vo.sort) == "time_desc">   selected </#if> >Add time</option> -->
                                        </select>
                                    </div><!-- /.form-group -->
                                </div>
                            </figure>
                        </section>
                    <section id="properties" class="display-lines">
                      <#list ps.list as house> 
                       
                            <div class="property">
                                <figure class="tag status">${house.typeStr}</figure>
                                <div class="property-image">
                                   
                                    <a href="/house/detail?id=${house.id}">
                                        <img alt="" src="${house.firstImg}" style="width: 260px;height: 195px" >
                                    </a>
                                </div>

                                <div class="info">
                                    <header>
                                        <a href="/house/detail?id=${house.id}"><h3>${house.name}</h3></a>
                                        <figure>${house.address}</figure>

                                    </header>
                                    <div class="tag price"> ${house.priceStr}</div>
                                    <aside>
                                         <p>${house.remarks}
                                        </p>

                                        <dl>
                                            <dt>Status:</dt>
                                                <dd>${house.typeStr}</dd>
                                            <dt>Area:</dt>
                                                <dd>${house.area} m<sup>2</sup></dd>
                                            <dt>Beds:</dt>
                                                <dd>${house.beds}</dd>
                                            <dt>Baths:</dt>
                                                <dd>${house.baths}</dd>
                                        </dl>
                                    </aside>
                                    <a href="/house/detail?id=${house.id}" class="link-arrow">Read More</a>
                                </div>
                            </div>
                        </#list>
                       </section>
                            <!-- Pagination -->
                            <div class="center">
                                 <@common.paging ps.pagination/>
                            </div><!-- /.center-->
                        </section><!-- /#properties-->
                    </section><!-- /#results -->
                </div><!-- /.col-md-9 -->
                <!-- end Results -->

                <!-- sidebar -->
                <div class="col-md-3 col-sm-3">
                    <section id="sidebar">
                        <aside id="edit-search">
                            <header><h3>Search Properties</h3></header>
                            
                            <form role="form" id="searchForm" class="form-search" method="post" action="/house/list">
                                <div class="form-group">
                                    <input type="text" class="form-control" id="search-box-property-id" value="${(vo.name)!}" name="name" placeholder="Search by City">
                                </div>
                                <div class="form-group">
                                    <select name="type">
                                        <option value="1" >Type</option>
                                        <option value="1" <#if (vo.type)?? && (vo.type)==1> selected </#if> >Sell</option>
                                        <option value="2" <#if (vo.type)?? && (vo.type)==2> selected </#if> >Rent</option>
                                    </select>
                                </div><!-- /.form-group -->
                                <input type="text" value="${(vo.sort)!}" name=sort hidden="true">
                               
                                <div class="form-group">
                                    <button type="submit" class="btn btn-default">Search</button>
                                </div><!-- /.form-group -->
                            </form><!-- /#form-map -->
                        </aside><!-- /#edit-search -->
                       
                        
                    </section><!-- /#sidebar -->
                </div><!-- /.col-md-3 -->
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
        })
      
      
  
     
      $('#sorting').change(function() {
           var type =  $(this).val();
           if (!type) {
               return;
           }
           window.location.href=  "/house/list?sort="+type+"&name=" + "${(vo.name)!}" + "&type=" + "${(vo.type)!0}" ;
       });

        
 </script>

</body>
</html>