<?php


namespace App\Helpers;


class ApiResponse
{
    private $data = [
        'error' => false,
        'error_msg' => ''
    ];

    public function setError($type,$error){
        $this->data['error'] = $type;
        $this->data['error_msg'] = $error;
    }
    
    public function setdata($index,$data){
        $this->data[$index] = $data;
    }

    public function getData(){
        return $this->data;
    }
}