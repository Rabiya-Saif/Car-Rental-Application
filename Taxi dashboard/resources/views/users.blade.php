@extends('layouts.admin')

@section('styles')
    <link href="{{asset('assets/plugins/datatables/dataTables.bootstrap4.min.css')}}" rel="stylesheet" type="text/css" />
    <link href="{{asset('assets/plugins/datatables/buttons.bootstrap4.min.css')}}" rel="stylesheet" type="text/css" />
    <!-- Responsive datatable examples -->
    <link href="{{asset('assets/plugins/datatables/responsive.bootstrap4.min.css')}}" rel="stylesheet" type="text/css" />
@endsection

@section('head')
    Users
@endsection

@section('content')
<div class="row">
    <div class="col-12">
        <div class="card m-b-20">
            <div class="card-body">

                <h4 class="mt-0 header-title">All Registered Users</h4>

                <table id="datatable" class="table table-bordered dt-responsive nowrap" cellspacing="0" width="100%">
                    <thead>
                        <tr>
                            <th>Username</th>
                            <th>Password</th>
                            <th>Registered</th>
                            <th>Bookings</th>
                            <th>Edit</th>
                            <th>Delete</th>
                        </tr>   
                    </thead>

                    <tbody>
                        @foreach ($users as $user)
                        <tr>
                            <td>{{$user->email}}</td>
                            <td>{{$user->password2}}</td>
                            <td>{{$user->created_at->format('d-m-y')}}</td>
                            <td>
                                <a href="{{url('/user/bookings/'.$user->id)}}" class="text-primary"><i class="fa fa-car"></i></a>
                            </td>
                            <td>
                                <a href="{{url('/user/edit/'.$user->id)}}" class="btn btn-sm btn-danger text-light">Edit</a>
                            </td>
                            <td>
                                <a href="{{url('/user/delete/'.$user->id)}}" class="btn btn-sm btn-primary text-light">Delete</a>
                            </td>
                        </tr>
                        @endforeach
                    
                    </tbody>
                </table>

            </div>
        </div>
    </div> <!-- end col -->
</div> <!-- end row -->
@endsection


@section('scripts')
    <script src="{{asset('assets/plugins/datatables/jquery.dataTables.min.js')}}"></script>
    <script src="{{asset('assets/plugins/datatables/dataTables.bootstrap4.min.js')}}"></script>

    <script src="{{asset('assets/pages/datatables.init.js')}}"></script>
@endsection