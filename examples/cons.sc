Nil

1 :: Nil

val foo = 1 :: 2 :: Nil
val bar = 3 :: 4 :: Nil

val baz = foo ::: bar

val bof = foo ::: 6 :: 7 :: Nil