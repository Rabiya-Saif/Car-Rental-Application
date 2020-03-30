@extends('layouts.admin')

@section('styles')
    <link href="{{asset('assets/plugins/datatables/dataTables.bootstrap4.min.css')}}" rel="stylesheet" type="text/css" />
    <link href="{{asset('assets/plugins/datatables/buttons.bootstrap4.min.css')}}" rel="stylesheet" type="text/css" />
    <!-- Responsive datatable examples -->
    <link href="{{asset('assets/plugins/datatables/responsive.bootstrap4.min.css')}}" rel="stylesheet" type="text/css" />
@endsection

@section('head')
    Cars
@endsection

@section('content')
<div class="row">
    <div class="col-12">
        <div class="card m-b-20">
            <div class="card-body">

                <h4 class="mt-0 header-title">All Registered Cars</h4>

                <table id="datatable" class="table table-bordered dt-responsive nowrap" cellspacing="0" width="100%">
                    <thead>
                        <tr>
                            <th>Number</th>
                            <th>Model</th>
                            <th>Company</th>
                            <th>Gas Inspection</th>
                            <th>Government Inspection</th>
                            <th>Bookings</th>
                            <th>Edit</th>
                            <th>Delete</th>
                        </tr>   
                    </thead>

                    <tbody>
                        @foreach ($cars as $car)
                        <tr>
                            <td>{{$car->number}}</td>
                            <td>{{$car->model}}</td>
                            <td>{{$car->company}}</td>
                            <td><span class="text-{{$car->gasInspection>Carbon\Carbon::today()?'success':'primary'}}"><i class="fa fa-{{$car->gasInspection>Carbon\Carbon::today()?'check':'close'}}">{{$car->gasInspection}}</i></span></td>
                            <td><span class="text-{{$car->policeInspection>Carbon\Carbon::today()?'success':'primary'}}"><i class="fa fa-{{$car->policeInspection>Carbon\Carbon::today()?'check':'close'}}"></i>{{$car->policeInspection}}</span></td>
                            <td>
                                <a href="{{url('/car/bookings/'.$car->id)}}" class="btn btn-sm btn-primary text-light">Bookings</a>
                            </td>
                            <td>
                                <a href="{{url('/car/edit/'.$car->id)}}" class="btn btn-sm btn-danger text-light">Edit</a>
                            </td>
                            <td>
                                <a href="{{url('/car/delete/'.$car->id)}}" class="btn btn-sm btn-primary text-light">Delete</a>
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