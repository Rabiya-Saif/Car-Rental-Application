<?php

use Illuminate\Database\Seeder;
use Illuminate\Support\Facades\DB;

class ComplainsTableSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        DB::table('complains')->insert([
            [
                'tag' => 'Center',
                'txt' => 'The Center side is damaged',
                'booking_id' => 1,
            ],
            [
                'tag' => 'Rear',
                'txt' => 'The Rear side is damaged',
                'booking_id' => 1,
            ],
            [
                'tag' => 'Front',
                'txt' => 'The Front side is damaged',
                'booking_id' => 1,
            ],
            [
                'tag' => 'Left',
                'txt' => 'The Left side is damaged',
                'booking_id' => 1,
            ],
            [
                'tag' => 'Right',
                'txt' => 'The Right side is damaged',
                'booking_id' => 1,
            ],
            [
                'tag' => 'Meter',
                'txt' => 'The Meter side is damaged',
                'booking_id' => 1,
            ],
            [
                'tag' => '3D1',
                'txt' => 'The 3D1 side is damaged',
                'booking_id' => 1,
            ],
            [
                'tag' => '3D2',
                'txt' => 'The 3D2 side is damaged',
                'booking_id' => 1,
            ],
        ]);
    }
}
