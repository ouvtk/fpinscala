def &&(p: Prop) = Prop {
  (n,rng) => run(n,rng) match {
    case Passed => p.run(n, rng)
    case x => x
  }
}

def ||(p: Prop) = Prop {
  (n,rng) => run(n,rng) match {
    // In case of failure, run the other prop.
    case Falsified(msg, _) => p.tag(msg).run(n,rng)
    case x => x
  }
}

/* This is rather simplistic - in the event of failure, we simply prepend
 * the given message on a newline in front of the existing message.
 */
def tag(msg: String) = Prop {
  (n,rng) => run(n,rng) match {
    case Falsified(e, c) => Falsified(msg + "\n" + e, c)
    case x => x
  }
}
