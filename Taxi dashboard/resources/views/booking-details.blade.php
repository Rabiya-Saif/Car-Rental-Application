@extends('layouts.admin')

@section('styles')
    <style>
        .car:hover{
            opacity: 0.5;
        }
        .cardIcon{
            font-size: 25px;
        }
        .holder{
            background-color: white;
        }
    </style>
@endsection

@section('head')
    Trip Details
@endsection

@section('content')

    <div class="row"> 
        <div class="col-lg-4 ">
            <div class="card m-b-20 h-100">
                <h4 class="card-header bg-primary text-light mt-0">Details</h4>
                <div class="card-body">
                    <ul class="list-group">
                        <li class="list-group-item d-flex justify-content-between align-items-center">
                            <span class="text-primary"><i class="cardIcon mdi mdi-calendar-clock"></i></span>    
                            {{$booking->created_at}}
                        </li>
                        <li class="list-group-item d-flex justify-content-between align-items-center">
                            <span class="text-primary"><i class="cardIcon fa fa-tachometer"></i></span>    
                            {{$booking->meterReading}} KMs
                        </li>   
                        <li class="list-group-item d-flex justify-content-between align-items-center">
                            <span class="text-primary"><i class="cardIcon fa fa-user"></i></span>    
                            {{$booking->user->email}}
                            
                        </li> 
                    </ul>
                </div>
            </div>
        </div>
        
        <div class="col-lg-4">
            <div class="card m-b-20 h-100">
                <h4 class="card-header bg-primary text-light mt-0">Wheels</h4>
                <div class="card-body">
                    <ul class="list-group">
                        <li class="list-group-item d-flex justify-content-between align-items-center">
                            <span class="text-dark">Front</span>
                            <span class="text-{{$booking->fLWheel?'success':'primary'}}"><i class=" mdi mdi-{{$booking->fLWheel?'check':'close'}}-circle">L</i></span>
                            <span class="text-{{$booking->fRWheel?'success':'primary'}}"><i class=" mdi mdi-{{$booking->fRWheel?'check':'close'}}-circle">R</i></span>
                        </li>
                        <li class="list-group-item d-flex justify-content-between align-items-center">
                            <span class="text-dark">Rear&nbsp;</span>
                            <span class="text-{{$booking->bLWheel?'success':'primary'}}"><i class=" mdi mdi-{{$booking->bLWheel?'check':'close'}}-circle">L</i></span>
                            <span class="text-{{$booking->bRWheel?'success':'primary'}}"><i class=" mdi mdi-{{$booking->bRWheel?'check':'close'}}-circle">R</i></span>
                        </li>
                        <li class="list-group-item d-flex justify-content-between align-items-center">
                            <span class="text-primary"><i class="cardIcon mdi mdi-comment-processing"></i></span>    
                            {{$booking->wheelComment}} 
                        </li>   
                    </ul>  
                </div>
            </div>
        </div>
        <div class="col-lg-4">
            <div class="card m-b-20 h-100" style="max-hieght:100;overflow-y:hidden">
                <h4 class="card-header bg-primary text-light mt-0">Comments</h4>
                <div class="card-body">
                    @if ($booking->problem)
                    <ul class="list-group">
                        <li class="list-group-item d-flex justify-content-between align-items-center">
                            <span class="text-primary"><i class="cardIcon mdi mdi-comment-processing"></i></span>
                                {{$booking->complains->first()->txt}}  
                        </li>                                                              
                    </ul>
                    <a class="mt-4 text-light btn btn-primary btn-block car" data-toggle="modal" data-target="#commentsModal">Show More</a>

                    @else
                    No Comments Availabe !
                    @endif
                </div>
            </div>
        </div>
    </div>

    <div class="row no-gutters holder">
        <div class="col-md-4 bg-light-strong">
            <span @if($booking->hasComplain('Rear'))
            data-toggle="tooltip" data-placement="top" title="{{$booking->getComplain('Rear')}}"
                @endif>
            <img src="{{asset('/images/default/'.($booking->hasComplain('Rear')?'bad':'good').'/rear.png')}}" 
            data-toggle="modal" data-target="#rearModal" class="car img-fluid " alt="smaple image">
            </span>
        </div>

        <div class="col-md-4">

            <span @if($booking->hasComplain('Meter'))
            data-toggle="tooltip" data-placement="top" title="{{$booking->getComplain('Meter')}}"
                @endif>
            <img src="{{asset('/images/default/'.($booking->hasComplain('Meter')?'bad':'good').'/meter.png')}}" 
            data-toggle="modal" data-target="#meterModal" class="car img-fluid " alt="smaple image">
            </span>
            
            <span @if($booking->hasComplain('Right'))
            data-toggle="tooltip" data-placement="top" title="{{$booking->getComplain('Right')}}"
                @endif>
            <img src="{{asset('/images/default/'.($booking->hasComplain('Right')?'bad':'good').'/right.png')}}" 
            data-toggle="modal" data-target="#rightModal" class="car img-fluid " alt="smaple image">
            </span>
            
            <span @if($booking->hasComplain('Center'))
            data-toggle="tooltip" data-placement="top" title="{{$booking->getComplain('Center')}}"
                @endif>
            <img src="{{asset('/images/default/'.($booking->hasComplain('Center')?'bad':'good').'/center.png')}}" 
            data-toggle="modal" data-target="#centerModal" class="car img-fluid " alt="smaple image">
            </span>



            {{-- <img src="{{asset('/images/default/'.($booking->hasComplain('Meter')?'bad':'good').'/meter.png')}}" 
            data-toggle="modal" data-target="#meterModal" class="car img-fluid " alt="smaple image"> 
            
            <img src="{{asset('/images/default/'.($booking->hasComplain('Right')?'bad':'good').'/right.png')}}" 
            data-toggle="modal" data-target="#rightModal" class="car img-fluid " alt="smaple image">
           
            <img src="{{asset('/images/default/'.($booking->hasComplain('Center')?'bad':'good').'/center.png')}}" 
            data-toggle="modal" data-target="#centerModal" class="car img-fluid " alt="smaple image"> --}}
        </div>
        
        <div class="col-md-4">
            <span @if($booking->hasComplain('Front'))
            data-toggle="tooltip" data-placement="top" title="{{$booking->getComplain('Front')}}"
                @endif>
            <img src="{{asset('/images/default/'.($booking->hasComplain('Front')?'bad':'good').'/front.png')}}" 
            data-toggle="modal" data-target="#frontModal" class="car img-fluid " alt="smaple image">
            </span>

            {{-- <img src="{{asset('/images/default/'.($booking->hasComplain('Front')?'bad':'good').'/front.png')}}" 
            data-toggle="modal" data-target="#frontModal" class="car img-fluid " alt="smaple image"> --}}
        </div>
    </div>

    <div class="row holder">
        <div class="col-md-4">
            <span @if($booking->hasComplain('3D1'))
            data-toggle="tooltip" data-placement="top" title="{{$booking->getComplain('3D1')}}"
                @endif>
            <img src="{{asset('/images/default/'.($booking->hasComplain('3D1')?'bad':'good').'/3d1.png')}}" 
            data-toggle="modal" data-target="#front3D" class="car img-fluid " alt="smaple image">
            </span>

            {{-- <img src="{{asset('/images/default/'.($booking->hasComplain('3D1')?'bad':'good').'/3d1.png')}}" 
            data-toggle="modal" data-target="#front3D" class="car img-fluid " alt="smaple image">  --}}
        </div>
        <div class="col-md-4">
            <span @if($booking->hasComplain('Left'))
            data-toggle="tooltip" data-placement="top" title="{{$booking->getComplain('Left')}}"
                @endif>
            <img src="{{asset('/images/default/'.($booking->hasComplain('Left')?'bad':'good').'/left.png')}}" 
            data-toggle="modal" data-target="#leftModal" class="car img-fluid " alt="smaple image">
            </span>
            
            {{-- <img src="{{asset('/images/default/'.($booking->hasComplain('Left')?'bad':'good').'/left.png')}}" 
            data-toggle="modal" data-target="#leftModal" class="car img-fluid " alt="smaple image"> --}}
        </div>
        <div class="col-md-4">
            <span @if($booking->hasComplain('3D2'))
            data-toggle="tooltip" data-placement="top" title="{{$booking->getComplain('3D2')}}"
                @endif>
            <img src="{{asset('/images/default/'.($booking->hasComplain('3D2')?'bad':'good').'/3d2.png')}}" 
            data-toggle="modal" data-target="#rear3D" class="car img-fluid " alt="smaple image">
            </span>
            
            
            {{-- <img src="{{asset('/images/default/'.($booking->hasComplain('3D2')?'bad':'good').'/3d2.png')}}" 
            data-toggle="modal" data-target="#rear3D" class="car img-fluid " alt="smaple image">  --}}
        </div>
    </div>

    <div id="commentsModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title mt-0" id="myLargeModalLabel">Complains</h5>
                        <button type="button" class="close text-primary" data-dismiss="modal" aria-hidden="true">×</button>
                    </div>
                    <div class="modal-body">
                        @foreach ($booking->complains as $complain)
                            <ul>
                                <li>{{$complain->txt}}</li>
                            </ul>   
                        @endforeach
                    </div>
                </div>
            </div>
        </div>

    <div id="rearModal" class="modal" >
        <div class="modal-dialog modal-full" style="min-width:80.5%">
            <div class="modal-content" >
                <div class="modal-body">
                        <i class="fa fa-close pull-right" data-dismiss="modal"></i>
                    <p><b>Comment : </b>"{{$booking->getComplain('Rear')}}"</p>
                    <img style="background: url('{{asset($booking->rearImage)}}')" class="img"
                     src="{{asset('/images/default/frames/van4.png')}}" style="max-width:100%" />
                </div>
            </div>
        </div>
    </div>
    
    <div id="rightModal" class="modal" >
        <div class="modal-dialog modal-full" style="min-width:80.5%">
            <div class="modal-content" >
                <div class="modal-body">
                        <i class="fa fa-close pull-right" data-dismiss="modal"></i>
                    <p><b>Comment : </b>"{{$booking->getComplain('Rear')}}"</p>
                    <img style="background: url('{{asset($booking->rightImage)}}')" class="img"
                     src="{{asset('/images/default/frames/van1.png')}}" style="max-width:100%" />
                </div>
                
            </div>
        </div>
    </div>
    
    <div id="meterModal" class="modal" >
        <div class="modal-dialog modal-full" style="min-width:80.5%">
            <div class="modal-content" >
                <div class="modal-body">
                        <i class="fa fa-close pull-right" data-dismiss="modal"></i>
                    <p><b>Comment : </b>"{{$booking->getComplain('Rear')}}"</p>
                    <img style="background: url('{{asset($booking->meterImage)}}')" class="img"
                     src="{{asset('/images/default/frames/vanone.png')}}" style="max-width:100%" />
                </div>
                
            </div>
        </div>
    </div>
    
    <div id="leftModal" class="modal" >
        <div class="modal-dialog modal-full" style="min-width:80.5%">
            <div class="modal-content" >
                <div class="modal-body">
                        <i class="fa fa-close pull-right" data-dismiss="modal"></i>
                    <p><b>Comment : </b>"{{$booking->getComplain('Rear')}}"</p>
                    <img style="background: url('{{asset($booking->leftImage)}}')" class="img"
                     src="{{asset('/images/default/frames/van3.png')}}" style="max-width:100%" />
                </div>
                
            </div>
        </div>
    </div>
    
    <div id="frontModal" class="modal" >
        <div class="modal-dialog modal-full" style="min-width:80.5%">
            <div class="modal-content" >
                <div class="modal-body">
                    <i class="fa fa-close pull-right" data-dismiss="modal"></i>
                    <p><b>Comment : </b>"{{$booking->getComplain('Rear')}}"</p>
                    <img style="background: url('{{asset($booking->frontImage)}}')" class="img"
                     src="{{asset('/images/default/frames/van5.png')}}" style="max-width:100%" />
                </div>
                
            </div>
        </div>
    </div>
    
    <div id="centerModal" class="modal" >
        <div class="modal-dialog modal-full" style="min-width:80.5%">
            <div class="modal-content" >
                <div class="modal-body">
                    <i class="fa fa-close pull-right" data-dismiss="modal"></i>
                    <p><b>Comment : </b>"{{$booking->getComplain('Rear')}}"</p>
                    <img style="background: url('{{asset($booking->centerImage)}}')" class="img"
                     src="{{asset('/images/default/frames/van2.png')}}" style="max-width:100%" />
                </div>
                
            </div>
        </div>
    </div>
    
    <div id="rear3D" class="modal" >
        <div class="modal-dialog modal-full" style="min-width:80.5%">
            <div class="modal-content" >
                <div class="modal-body">
                    <i class="fa fa-close pull-right" data-dismiss="modal"></i>
                    <p><b>Comment : </b>"{{$booking->getComplain('Rear')}}"</p>
                    <img style="background: url('{{asset($booking->image3D1)}}')" class="img"
                     src="{{asset('/images/default/frames/vanseven.png')}}" style="max-width:100%" />
                </div>
                
            </div>
        </div>
    </div>
    
    
    <div id="front3D" class="modal" >
        <div class="modal-dialog modal-full" style="min-width:80.5%">
            <div class="modal-content" >
                <div class="modal-body">
                    <i class="fa fa-close pull-right" data-dismiss="modal"></i>
                    <p><b>Comment : </b>"{{$booking->getComplain('Rear')}}"</p>
                    <img style="background: url('{{asset($booking->image3D2)}}')" class="img"
                     src="{{asset('/images/default/frames/vansix.png')}}" style="max-width:100%" />
                </div>
                
            </div>
        </div>
    </div>
    
@endsection

@section('scripts')

    {{-- <script>
        $(document).ready(function () {
            $('#rearModal').on('show.bs.modal', function (e) {
                var image = $(e.relatedTarget).attr('src');
            });
        });
    </script> --}}

@endsection