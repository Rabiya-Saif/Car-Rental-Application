@extends('layouts.admin')

@section('styles')
    <link href="{{asset('assets/plugins/datatables/dataTables.bootstrap4.min.css')}}" rel="stylesheet" type="text/css" />
    <link href="{{asset('assets/plugins/datatables/buttons.bootstrap4.min.css')}}" rel="stylesheet" type="text/css" />
    <!-- Responsive datatable examples -->
    <link href="{{asset('assets/plugins/datatables/responsive.bootstrap4.min.css')}}" rel="stylesheet" type="text/css" />
@endsection

@section('head')
    Trips
@endsection

@section('content')
<div class="row">
    <div class="col-12">
        <div class="card m-b-20">
            <div class="card-body">

                <h4 class="mt-0 header-title">Trips</h4>

                <table id="datatable" class="table table-bordered dt-responsive nowrap" cellspacing="0" width="100%">
                    <thead>
                        <tr>
                            <th>Car</th>
                            <th>Driver</th>
                            <th>Reading</th>
                            <th>Time</th>
                            <th>View</th>
                        </tr>   
                    </thead>

                    <tbody>
                        @foreach ($bookings as $booking)
                        <tr>
                            <td>{{$booking->car->number}}</td>
                            <td>{{$booking->user->email}}</td>
                            <td>{{$booking->meterReading}}</td>
                            <td>{{$booking->created_at}}</td>
                            <td>
                                <a href="{{url('/bookings/detail/'.$booking->id)}}" class="btn btn-sm btn-danger text-light">Details</a>
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

    {{-- <script src="{{asset('assets/pages/datatables.init.js')}}"></script> --}}

    <script>
    $(document).ready(function(){
        $('#datatable').DataTable({
            "order": [[ 3, "desc" ]]
        });
    });
    </script>
    
@endsection