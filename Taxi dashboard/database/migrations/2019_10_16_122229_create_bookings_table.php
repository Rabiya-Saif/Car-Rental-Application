<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateBookingsTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('bookings', function (Blueprint $table) {
            $table->bigIncrements('id');
            $table->unsignedBigInteger('car_id');
            $table->foreign('car_id')->references('id')->on('cars')->onDelete('cascade');
            $table->unsignedBigInteger('user_id');
            $table->foreign('user_id')->references('id')->on('users')->onDelete('cascade');
            $table->string('meterReading');
            $table->boolean('gasInspection')->default(false);
            $table->boolean('policeInspection')->default(false);
            $table->text('wheelComment')->nullable();
            $table->boolean('problem')->default(false);
            $table->boolean('fRWheel')->default(false);
            $table->boolean('fLWheel')->default(false);
            $table->boolean('bRWheel')->default(false);
            $table->boolean('bLWheel')->default(false);
            $table->string('frontImage')->nullable();
            $table->string('rearImage')->nullable();
            $table->string('leftImage')->nullable();
            $table->string('rightImage')->nullable();
            $table->string('centerImage')->nullable();
            $table->string('meterImage')->nullable();
            $table->string('image3D1')->nullable();
            $table->string('image3D2')->nullable();
            $table->timestamps();
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('bookings');
    }
}
