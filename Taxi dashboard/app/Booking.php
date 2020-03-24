<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Booking extends Model
{
    public function car(){
        return $this->belongsTo('App\Car');
    }

    public function user(){
        return $this->belongsTo('App\User');
    }

    public function complains(){
        return $this->hasMany('App\Complain');
    }

    public function hasComplain($part){
        foreach($this->complains as $complain){
            if($complain->tag == $part){
                return true;
            }
        }
        return false;
    }
    
    public function getComplain($part){
        foreach($this->complains as $complain){
            if($complain->tag === $part){
                return $complain->txt;
            }
        }

    }

}
