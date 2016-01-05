#!/bin/bash

PACKAGE="net.iubris.conti"


### do not touch below

if [ "$1" == "-h" ]; then
   echo "logcat.sh [-e] [INDEX]"
   echo -e "\t -e = use first emulator instance"
   echo -e "\t INDEX [integer number, starting from 1] = use i-nth device instance (emulator or real device)"
   echo
   echo "with no arguments it uses real device"
   exit 1 
fi

if [ "$1" == '-e' ]; then EMU='-e'; fi
if [ "$1" == '-d' ]; then DEV='-d'; fi


declare -x args=("$@")
index="${args[1]}"


if [ ! -z $EMU ]; then
   emulator_lines=$(adb devices | grep emulator | awk '{print $1}')
   if [ ! -z "${index}" ]; then
      how_lines=$(echo $emulator_lines | wc -w)
      if [ ${index} -gt $how_lines ]; then
         echo index specified is too great, exiting
         exit 1
      fi

      emulator_instance=$(echo $emulator_lines  | awk "{print $"${index}"}")
      EMU="-s $emulator_instance"
   else 
      if [ $(adb devices | grep emulator | wc -l) -gt 2 ]; then
         echo more than one emulator/device instances are running. you have to choice which one, using arguments 'e' or 'INT_NUMBER'
         exit 1
      fi
   fi
fi

if [ -z "${EMU}" ]; then
   if [ -z "${DEV}" ]; then
		real=$(adb devices | grep device | grep -v emulator | grep -v List | awk '{print $1}')
		#echo $real
		DEV="-s ${real}"
		if [ -z "${real}" ]; then 
				echo "no real device found; exiting"; exit 1;
		fi
   else
		[ -z $2 ] && echo "no real device ID specified; exiting" && exit 1
		DEV="-s "${2}
   fi 
fi


function getPid() {
   echo $(adb ${DEV} ${EMU} shell ps | grep $PACKAGE | cut -c10-15)
}


app_pid=$(getPid)
adb_pid=""
while (true); do
	app_pid=$(getPid)
	if [ -z "${app_pid}" ]; then
		echo `date` " waiting for app..."
		sleep 5
		app_pid=$(getPid)
		
      [ ! -z $adb_pid ] && ( kill -9 $adb_pid >/dev/null 2>&1 ; echo -e "\n\n\n\n" )
		adb_pid=""
	else
		if [ -z $adb_pid ]; then
         echo -e "\n\n"
			echo "app pid: ${app_pid}"
			adb $DEV $EMU logcat | grep $app_pid &
			adb_pid=$!
			echo "adb logcat pid: ${adb_pid}"
		fi
	fi
done
