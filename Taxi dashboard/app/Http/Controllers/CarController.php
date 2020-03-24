<?php

namespace App\Http\Controllers;

use App\Booking;
use App\Car;
use App\Helpers\ApiResponse;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Validator;

class CarController extends Controller
{
    public function carBookings($id){
        $bookings = Car::find($id)->bookings;
        return view('bookings',compact('bookings'));
    }


    public function addCar(Request $request){
        $validator = Validator::make($request->all(), [
            'number' => 'required|unique:cars',
        ]);

        if($validator->fails()){
            toastr()->error('Car already exists','Error');
        }else{
            $car = new Car();
            $car->number = $request->number;
            $car->model = $request->model;
            $car->company = $request->company;
            $car->gasInspection = $request->gasInspection;
            $car->policeInspection = $request->policeInspection;
            $car->save();
            toastr()->success('Car added successfuly','Done');
        }
        
        return redirect()->back();
    }
    
    public function updateCar(Request $request){   
        $car = Car::find($request->id);
        $car->number = $request->number;
        $car->model = $request->model;
        $car->company = $request->company;
        $car->policeInspection = $request->policeInspection;
        $car->gasInspection = $request->gasInspection;
        $car->save();

        toastr()->success('Car updated successfuly','Done');
        return redirect('/car/all');
    }

    public function deleteCar($id){
        Car::find($id)->delete();
        toastr()->success('Car deleted successfuly','Done');
        return redirect()->back();
    }

    public function getCars(){
        $response = new ApiResponse();
        $cars = Car::all()->pluck('number');
        $response->setError(false,'Request Successfull');
        $response->setdata('cars', $cars);

        return response()->json($response->getData());
    }

}
