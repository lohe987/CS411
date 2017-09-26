/*
 * main.c
 *
 *  Created on: Aug 23, 2016
 *      Author: zzhang
 */

#include <stdint.h>
#include <stdbool.h>
#include <stdio.h>

#include "launchpad.h"
#include "seg7.h"

// 7-segment coding table. See https://en.wikipedia.org/wiki/Seven-segment_display. The segments
// are named as A, B, C, D, E, F, G. In this coding table, segments A-G are mapped to bits 0-7.
// Bit 7 is not used in the coding. This display uses active high signal, in which '1' turns ON a
// segment, and '0' turns OFF a segment.
static uint8_t seg7Coding[10] = {
		0b00111111, 		// digit 0
		0b00000110, 		// digit 1
		0b01011011,			// digit 2
		0b01001111,			// digit 3
		0b01100110,			// digit 4
		0b01101101,			// digit 5
		0b01111101,			// digit 6
		0b00000111,			// digit 7
		0b01111111,			// digit 8
		0b01101111			// digit 9
};

// The four display digits, for seconds and centiseconds
uint8_t s1 = 0, s2 = 0, m1 = 0, m2 = 0;

// The colon status: if colon == 0b10000000, then the colon is on.
// In this lab, the colon is always on.
static uint8_t colon = 0b10000000;

// Update the clock display
void
clockUpdate(uint32_t time)							// pointer to a 4-byte array
{
	uint8_t code[4];									// The 7-segment code for the four clock digits

	// Display the current clock digits
	code[0] = seg7Coding[s1] + colon;
	code[1] = seg7Coding[s2] + colon;
	code[2] = seg7Coding[m1] + colon;
	code[3] = seg7Coding[m2] + colon;
	seg7Update(code);

	// Calculate the display digits and colon setting for the next update
	if (colon == 0b00000000) {
		colon = 0b10000000;
	} else {
		colon = 0b00000000;
		if (++s1 >= 10) {
			s1 = 0;
			if (++s2 >= 6) {
				s2 = 0;
				if (++m1 >= 10) {
					m1 = 0;
					if (++m2 >= 6) {
						m2 = 0;
					}
				}
			}
		}
	}

	// Call back after 1 second
	schdCallback(clockUpdate, time + 500);
}

// Event driven code for checking push button
void
checkPushButton(uint32_t time)
{
	int code = pbRead();
	uint32_t delay;

	// FIXME: The clock display is not updated immediately after
	// a fastforward change. This can be fixed by doing an encoding
	// and call seg7Update();
	switch (code) {
	case 1:					// Update minute digit
		if (++m1 >= 10) {
			m1 = 0;
			if (++m2 >= 6) {
				m2 = 0;
			}
		}
		delay = 150;
		break;

	case 2:
		if (++s1 >= 10) {
			s1 = 0;
			if (++s2 >= 6) {
				s2 = 0;
			}
		}
		delay = 150;							// Use an inertia for soft debouncing
		break;

	default:
		delay = 10;
	}

	schdCallback(checkPushButton, time + delay);
}

int main(void)
{
	lpInit();
	seg7Init();

	uprintf("%s\n\r", "Lab 2: Wall clock");

	// Schedule the first callback events for LED flashing and push button checking.
	// Those trigger callback chains. The time unit is millisecond.
	schdCallback(clockUpdate, 1000);
	schdCallback(checkPushButton, 1005);

	// Loop forever
	while (true) {
		schdExecute();
	}
}
