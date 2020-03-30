<?php

use Carbon\Traits\Creator;
use Illuminate\Database\Seeder;
use Illuminate\Support\Carbon;
use Illuminate\Support\Facades\DB;

class BookingsTableSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        DB::table('bookings')->insert([
            'car_id' => 1,
            'user_id' => 1,
            'meterReading' => rand(111111,999999),
            'problem' => false,
            'wheelComment' => 'Wheels are ok',
            'fRWheel' => true,
            'fLWheel' => false,
            'bRWheel' => false,
            'bLWheel' => true,
            'frontImage' => '/images/front.png',
            'rearImage' => '/images/rear.png',
            'leftImage' => '/images/left.png',
            'rightImage' => '/images/right.png',
            'centerImage' => '/images/center.png',
            'meterImage' => '/images/meter.png',
            'image3D1' => '/images/3d1.png',
            'image3D2' => '/images/3d2.png',
            'created_at' => now()
        ]);
    }
}
