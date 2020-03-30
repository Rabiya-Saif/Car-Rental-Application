@extends('layouts.admin')
 
 @section('head')
    User    
 @endsection
 
 @section('content')
 <div class="row">
    <div class="col-lg-6">
        <div class="card m-b-20">
            <div class="card-body">

                <h4 class="mt-0 header-title">Add New Car</h4>
                <form autocomplete="off" method="POST" class="" action="{{url('/car/add')}}">
                    
                    <div class="form-group">
                        <label>Number</label>
                        <div>
                            <input name="number" type="text" class="form-control" placeholder="XX-XX-1234"/>
                        </div>
                    </div>
                
                    <div class="form-group">
                        <label>Model</label>
                        <div>
                            <input name="model" type="number" class="form-control" placeholder="E.g 2015"/>
                        </div>
                    </div>
                    
                    <div class="form-group">
                        <label>Company</label>
                        <div>
                            <input name="company" type="text" class="form-control" placeholder="Company Name"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label>Gas Inspection</label>
                        <input value="" name="gasInspection" type="date" class="form-control"/>
                    </div>
                    
                    <div class="form-group">
                        <label>Government Inspection</label>
                        <input name="policeInspection" type="date" class="form-control"/>
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