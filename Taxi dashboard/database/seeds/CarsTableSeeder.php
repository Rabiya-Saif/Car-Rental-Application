<?php

use Illuminate\Database\Seeder;
use Illuminate\Support\Carbon;
use Illuminate\Support\Facades\DB;

class CarsTableSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        DB::table('cars')->insert([
            'number' => "1-1-1",
            'model' => "2019",
            'company' => "Toyota",
            'gasInspection' => Carbon::today(),
            'policeInspection' => Carbon::today()
        ]);
    }
}
