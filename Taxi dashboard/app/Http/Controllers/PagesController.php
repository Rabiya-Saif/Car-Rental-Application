<?php

namespace App\Http\Controllers;

use App\Admin;
use App\Booking;
use App\Car;
use App\User;
use Illuminate\Http\Request;

class PagesController extends Controller
{
    public function showLogin(){
        return view('login');
    }

    public function showDashboard(){
        $cars = Car::all()->count();
        $users = User::all()->count();
        $bookings = Booking::all()->count();
        $admins = Admin::all()->count();
        return view('dashboard',compact('cars','users','bookings','admins'));
    }
    //////////////////////BOOKINGS////////////////////
    public function showBookings(){
        $bookings = Booking::all();
        return view('bookings',compact('bookings'));
    }
    
    public function showBookingDetail($id){
        $booking = Booking::find($id);
        return view('booking-details',compact('booking'));
    }
    //////////////////////////////////////////////////


    //////////////////////USERS///////////////////////
    public function showNewUser(){
        return view('new-user');
    }
    
    public function showUsers(){
        $users = User::all();
        return view('users',compact('users'));
    }
    
    public function showEditUser($id){
        $user = User::find($id);
        return view('edit-user',compact('user'));
    }
    //////////////////////////////////////////////////
    

    //////////////////////CARS////////////////////////
    public function showNewCar(){
        return view('new-car');
    }
    
    public function showCars(){
        $cars = Car::all();
        return view('cars',compact('cars'));
    }
    
    public function showEditCar($id){
        $car = Car::find($id);
        return view('edit-car',compact('car'));
    }
    ///////////////////////////////////////////////////
    
}
