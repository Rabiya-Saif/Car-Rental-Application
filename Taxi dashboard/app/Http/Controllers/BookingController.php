<?php

namespace App\Http\Controllers;

use App\Booking;
use App\Car;
use App\Complain;
use App\Helpers\ApiResponse;
use App\Helpers\ImageHelper;
use App\TemporaryBooking;
use App\User;
use Carbon\Carbon;
use Illuminate\Http\Request;

class BookingController extends Controller
{

    public function addCarNumber(Request $request){
        $response = new ApiResponse();
        $car = Car::where('number', '=', $request->number);

        if($car->exists()){
            $car = $car->first();
            $booking = new TemporaryBooking();
            $booking->user_id = $request->userId;
            $booking->car_id = $car->id;
            $booking->save();
         
            $response->setError(false,'Request Successfull');
            $response->setdata('bookingId',$booking->id);
        }else{
            $response->setError(true,'Car not found!');
        }        
        
        return response()->json($response->getData());
    }

    public function saveImage(Request $request){
        $response = new ApiResponse();
        $tempBooking = TemporaryBooking::find($request->bookingId);

        $this->saveImageWithName($request,$tempBooking);
        $response->setError('false','image saved');

        return response()->json($response->getData());
    }   
    
    public function addComplian(Request $request){
        $response = new ApiResponse();
        $tempBooking = TemporaryBooking::find($request->bookingId);

        if($request->complain){
            $complain = new Complain();
            $complain->txt = $request->complain;
            $complain->tag = $request->tag;
            $complain->temporary_booking_id = $tempBooking->id;
            $complain->save();

            $tempBooking->problem = true;
            $tempBooking->save();
            $response->setError('false','complain saved');
        }else{
            $response->setError('false','No Complain made');
        }
        return response()->json($response->getData());
    }
    
    public function addTyre(Request $request){
        $response = new ApiResponse();
        $tempBooking = TemporaryBooking::find($request->bookingId);
        $tempBooking->meterReading = $request->meterReading;
        $tempBooking->fRWheel = $request->fRWheel;
        $tempBooking->fLWheel = $request->fLWheel;
        $tempBooking->bRWheel = $request->bRWheel;
        $tempBooking->bLWheel = $request->bLWheel;
        $tempBooking->wheelComment =$request->wheelComment;
        $tempBooking->save();

        $final = $this->finalizeBooking($tempBooking);
        
        // if($tempBooking->problem){
        //     $this->sendMail($final);
        // }

        $tempBooking->delete();
        $response->setError('false','Tyre saved');
        return response()->json($response->getData());
    }
    
    public function getBookings(Request $request){
        $response = new ApiResponse();
        $bookings = Booking::where('user_id',$request->userId)->with('car')->latest()->take(5)->get();

        $response->setdata('carModel',$bookings);
        $response->setError('false','Data fetched');
        return response()->json($response->getData());
    }

    /////////////////////////////Private MEthods/////////////////////
    private function sendMail($booking){
        $data = ['booking' => $booking];
        Mail::send('mail', $data, function ($message ){
            $message->from('taxi@triteckodes.store', 'Taxi');
            $message->to('iwaheedshahzad@gmail.com', 'Taxi App')
            ->subject('Complains');
        });
    }

    private function finalizeBooking($booking){
        $finalBooking = new Booking();
        $finalBooking->user_id = $booking->user_id;
        $finalBooking->car_id = $booking->car_id;
        $finalBooking->meterReading = $booking->meterReading;
        $finalBooking->wheelComment = $booking->wheelComment;
        $finalBooking->problem = $booking->problem;
        $finalBooking->fRWheel = $booking->fRWheel;
        $finalBooking->fLWheel = $booking->fLWheel;
        $finalBooking->bRWheel = $booking->bRWheel;
        $finalBooking->bLWheel = $booking->bLWheel;
        $finalBooking->frontImage = $booking->frontImage;
        $finalBooking->rearImage = $booking->rearImage;
        $finalBooking->leftImage = $booking->leftImage;
        $finalBooking->rightImage = $booking->rightImage;
        $finalBooking->centerImage = $booking->centerImage;
        $finalBooking->meterImage = $booking->meterImage;
        $finalBooking->image3D1 = $booking->image3D1;
        $finalBooking->image3D2 = $booking->image3D2;
        $finalBooking->save();

        foreach($booking->complains as $complain){
            $complain->booking_id = $finalBooking->id;
            $complain->temporary_booking_id = null;
            $complain->save();
        }

        return $finalBooking;
    }

    private function saveImageWithName($request,&$tempBooking){
        switch($request->name){
            case 'Front':
                $tempBooking->frontImage = ImageHelper::saveImage($request->file,'/images/');
                break;
            case 'Left':
                $tempBooking->leftImage = ImageHelper::saveImage($request->file,'/images/');
                break;
            case 'Right':
                $tempBooking->rightImage = ImageHelper::saveImage($request->file,'/images/');
                break;
            case 'Rear':
                $tempBooking->rearImage = ImageHelper::saveImage($request->file,'/images/');
                break;
            case 'Center':
                $tempBooking->centerImage = ImageHelper::saveImage($request->file,'/images/');
                break;
            case 'Meter':
                $tempBooking->meterImage = ImageHelper::saveImage($request->file,'/images/');
                break;
            case '3D1':
                $tempBooking->image3D1 = ImageHelper::saveImage($request->file,'/images/');
                break;
            case '3D2':
                $tempBooking->image3D2 = ImageHelper::saveImage($request->file,'/images/');
                break;
        }

        $tempBooking->save();
    }
    
    ///////////////////////////////////////////////////////////////////
}