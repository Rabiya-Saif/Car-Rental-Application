<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class TemporaryBooking extends Model
{
    public function car(){
        return $this->belongsTo('App\Car');
    }
    
    public function complains(){
        return $this->hasMany('App\Complain');
    }
}
