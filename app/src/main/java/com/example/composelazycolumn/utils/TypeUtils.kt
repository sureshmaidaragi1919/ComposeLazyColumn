package com.example.composelazycolumn.utils

//Learning purpose, Simple replacements for enum types
sealed class TypeUtils(val name: String) {
    class Hyundai : TypeUtils("Hyundai")
    class Chevrolet : TypeUtils("Chevrolet")
    class Mustang : TypeUtils("Mustang")
}

class AstonMartin : TypeUtils("Aston Martin")

fun main() {
    display(TypeUtils.Mustang())
    display(AstonMartin())
}

fun display(typeUtils: TypeUtils) {

    when (typeUtils) {
        is TypeUtils.Hyundai -> {
            println(typeUtils.name)
        }

        is TypeUtils.Chevrolet -> {
            println(typeUtils.name)
        }

        is TypeUtils.Mustang -> {
            println(typeUtils.name)
        }

        is AstonMartin -> {
            println(typeUtils.name)
        }
    }
}