<?php

namespace App\Http\Controllers;

use App\Admin;
use App\Helpers\ApiResponse;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;

class AuthController extends Controller
{
    public function loginAdmin(Request $request){
        $credentials = [
            'name' => $request->name,
            'password' => $request->password,
        ];
        
       if(Auth::guard('admin')->attempt($credentials))
       {
           return redirect('/');
       }else
       {
           redirect()->back()->with('msg','Wrong username or password');
       }
    }

    public function userLogin(Request $request){
        $response = new ApiResponse();
        $credentials = [
            'email' => $request->email,
            'password' => $request->password,
        ];
        
       if(Auth::guard('user')->attempt($credentials))
       {
            $response->setdata('id', Auth::guard('user')->user()->id);
            $response->setError(false,'Login Successful');
       }else{
            $response->setError(true,'Wrong email or password');
       }

       return response()->json($response->getData());
    }

    public function logoutAdmin(){
        Auth::logout();
        return redirect('/login');
    }


}
