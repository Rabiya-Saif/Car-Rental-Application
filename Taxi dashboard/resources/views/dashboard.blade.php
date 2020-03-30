@extends('layouts.admin')

@section('head')
    Dashboard
@endsection

@section('content')
    <div class="container-fluid">

        <div class="row">
            <div class="col-md-4 col-lg-4 col-xl-4">
                <div class="mini-stat clearfix bg-primary">
                    <span class="mini-stat-icon"><i class="mdi mdi-car"></i></span>
                    <div class="mini-stat-info text-right text-white">
                        <span class="counter">{{$cars}}</span>
                        Total Cars
                    </div>
                </div>
            </div>
            <div class="col-md-4 col-lg-4 col-xl-4">
                <div class="mini-stat clearfix bg-primary">
                    <span class="mini-stat-icon"><i class="fa fa-users"></i></span>
                    <div class="mini-stat-info text-right text-white">
                        <span class="counter">{{$users}}</span>
                        Registered Users
                    </div>
                </div>
            </div>
            <div class="col-md-4 col-lg-4 col-xl-4">
                <div class="mini-stat clearfix bg-primary">
                    <span class="mini-stat-icon"><i class="fa fa-list-ul"></i></span>
                    <div class="mini-stat-info text-right text-white">
                        <span class="counter">{{$bookings}}</span>
                        Total Trips
                    </div>
                </div>
            </div>
            
        </div>

    </div><!-- container-fluid -->
@endsection