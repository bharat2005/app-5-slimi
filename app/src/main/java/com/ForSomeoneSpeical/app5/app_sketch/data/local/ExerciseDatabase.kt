package com.ForSomeoneSpeical.app5.app_sketch.data.local

import com.ForSomeoneSpeical.app5.app_sketch.domain.model.Exercise

object ExerciseDatabase {
    val exercises = listOf<Exercise>(
        Exercise("Running (6 mph)", 10.0),
        Exercise("Light Jogging", 7.0),
        Exercise("Walking (3 mph)", 3.5),
        Exercise("Cycling (moderate)", 8.0),
        Exercise("Weightlifting (general)", 3.0),
        Exercise("Circuit Training (vigorous)", 8.0),
        Exercise("Yoga (Hatha)", 2.5),
        Exercise("Swimming (freestyle)", 7.0),
        Exercise("Jumping Jacks", 8.0),
        Exercise("Stretching", 2.0)
    )
}