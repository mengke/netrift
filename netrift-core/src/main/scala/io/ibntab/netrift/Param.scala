package io.ibntab.netrift

/**
  * Created by ke.meng on 2016/4/27.
  *
  */
trait Param[P] {
  def default: P
}
object Param {
  def apply[T](t: => T): Param[T] = new Param[T] {
    // Note, this is lazy to avoid potential failures during
    // static initialization.
    lazy val default = t
  }
}

trait Params extends Iterable[(Param[_], Any)] {
  /**
    * Get the current value of the P-typed parameter.
    */
  def apply[P: Param]: P

  /**
    * Returns true if there is a non-default value for
    * the P-typed parameter.
    */
  def contains[P: Param]: Boolean

  /**
    * Iterator of all `Param`s and their associated values.
    */
  def iterator: Iterator[(Param[_], Any)]

  /**
    * Produce a new parameter map, overriding any previous
    * `P`-typed value.
    */
  def +[P: Param](p: P): Params

  /**
    * Alias for [[addAll(Params)]].
    */
  def ++(ps: Params): Params =
    addAll(ps)

  /**
    * Produce a new parameter map, overriding any previously
    * mapped values.
    */
  def addAll(ps: Params): Params

}

object Params {
  private case class Prms(map: Map[Param[_], Any]) extends Params {
    def apply[P](implicit param: Param[P]): P =
      map.get(param) match {
        case Some(v) => v.asInstanceOf[P]
        case None => param.default
      }

    def contains[P](implicit param: Param[P]): Boolean =
      map.contains(param)

    def iterator: Iterator[(Param[_], Any)] =
      map.iterator

    def +[P](p: P)(implicit param: Param[P]): Params =
      copy(map + (param -> p))

    def addAll(ps: Params): Params =
      copy(map ++ ps.iterator)
  }

  /**
    * The empty parameter map.
    */
  val empty: Params = Prms(Map.empty)
}
