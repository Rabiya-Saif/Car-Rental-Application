<?php

namespace App\Http\Controllers;

use App\User;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Hash;
use Illuminate\Support\Facades\Validator;

class UserController extends Controller
{
    
    public function userBookings($id){
        $bookings = User::find($id)->bookings;
        return view('bookings',compact('bookings'));
    }

    public function addUser(Request $request){

        $validator = Validator::make($request->all(), [
            'email' => 'required|unique:users',
        ]);
        
        if($validator->fails()){
            toastr()->error('User already exists', 'Error');
            return redirect()->back();
        }else{
            $user = new User();
            $user->email = $request->email;
            $user->password2 = $request->password;
            $user->password = Hash::make($request->password);
            $user->save();

            toastr()->success('User added successfully', 'Done');
            return redirect()->back();
        }
    }
    
    public function updateUser(Request $request){
        $user = User::find($request->id);
        $user->email = $request->email;
        if($request->password != null){
            $user->password2 = $request->password; 
            $user->password = Hash::make($request->password); 
        }
        $user->save();

        return redirect('/user/all')->with('msg','User updated successfuly');
    }

    public function deleteUser($id){
        User::find($id)->delete();
        return redirect()->back()->with('msg','User deleted successfuly');
    }
}
