@extends('layouts.admin')
 
@section('head')
    Car
@endsection

@section('content')
 <div class="row">
    <div class="col-lg-6">
        <div class="card m-b-20">
            <div class="card-body">

                <h4 class="mt-0 header-title">Edit Car</h4>
                <form method="POST" class="" action="{{url('/car/update')}}">
                    
                    <input type="hidden" name="id" value="{{$car->id}}">
                    <div class="form-group">
                        <label>Number</label>
                        <input value="{{$car->number}}" name="number" type="text" class="form-control" placeholder="Enter Car Number"/>
                    </div>
                    
                    <div class="form-group">
                        <label>Company</label>
                        <input value="{{$car->company}}"  name="company" type="text" class="form-control" placeholder="Company name"/>
                    </div>
                
                    <div class="form-group">
                        <label>Model</label>
                        <input value="{{$car->model}}" name="model" type="number" class="form-control" placeholder="E.g 2015"/>
                    </div>

                    <div class="form-group">
                        <label>Gas Inspection</label>
                        <input value="{{$car->gasInspection}}" name="gasInspection" type="date" class="form-control"/>
                    </div>
                    
                    <div class="form-group">
                        <label>Government Inspection</label>
                        <input value="{{$car->policeInspection}}" name="policeInspection" type="date" class="form-control"/>
                    </div>
                
                    <div class="form-group">
                        <div class="pull-right">
                            <button type="submit" class="btn btn-primary waves-effect waves-light">
                                Submit
                            </button>
                            <a href="{{url('/car/all')}}" class="btn btn-secondary waves-effect m-l-5">
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