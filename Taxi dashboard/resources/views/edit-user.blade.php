@extends('layouts.admin')

@section('head')
    User
@endsection

 @section('content')
 <div class="row">
    <div class="col-lg-6">
        <div class="card m-b-20">
            <div class="card-body">

                <h4 class="mt-0 header-title">Edit User</h4>
                <form method="POST" class="" action="{{url('/user/update')}}">

                    <input type="hidden" name="id" value="{{$user->id}}">
                    <div class="form-group">
                        <label>Username</label>
                        <div>
                            <input value="{{$user->email}}" name="email" type="text" class="form-control"/>
                        </div>
                    </div>
                
                    <div class="form-group">
                        <label>Password</label>
                        <div>
                            <input value="{{$user->password2}}" name="password" type="password" class="form-control" placeholder="Optional"/>
                        </div>
                    </div>
                   
                    <div class="form-group">
                        <div>
                            <button type="submit" class="btn btn-primary waves-effect waves-light">
                                Submit
                            </button>
                            <a href="{{url('/user/all')}}" class="btn btn-secondary waves-effect m-l-5">
                                Cancel
                            </a>
                        </div>
                    </div>
                </form>

            </div>
        </div>
    </div> <!-- end col -->
</div> <!-- end row -->
 @endsection