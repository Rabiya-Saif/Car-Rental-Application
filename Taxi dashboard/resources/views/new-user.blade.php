@extends('layouts.admin')
 
 @section('head')
    User    
 @endsection
 
 @section('content')
 <div class="row">
    <div class="col-lg-6">
        <div class="card m-b-20">
            <div class="card-body">

                <h4 class="mt-0 header-title">Add new User</h4>
                <form autocomplete="off" method="POST" class="" action="{{url('/user/add')}}">
                    
                    <div class="form-group">
                        <label>Username</label>
                        <div>
                            <input name="email" type="text" class="form-control"/>
                        </div>
                    </div>
                
                    <div class="form-group">
                        <label>Password</label>
                        <div>
                            <input autocomplete="off" name="password" type="password" class="form-control" placeholder="Password"/>
                        </div>
                    </div>
                   
                    <div class="form-group">
                        <div>
                            <button type="submit" class="btn btn-primary waves-effect waves-light">
                                Submit
                            </button>
                            <button type="reset" class="btn btn-secondary waves-effect m-l-5">
                                Cancel
                            </button>
                        </div>
                    </div>
                </form>

            </div>
        </div>
    </div> <!-- end col -->
</div> <!-- end row -->
 @endsection