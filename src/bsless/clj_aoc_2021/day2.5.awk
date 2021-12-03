BEGIN {
    x=0;
    y=0;
    a=0
    t["f"]=0;
    t["u"]=-1;
    t["d"]=1;
    h["f"]=1;
    h["u"]=0;
    h["d"]=0;
    v["f"]=1;
    v["u"]=0;
    v["d"]=0;
}
{
    c=substr($1,1,1)
    a = a + t[c]*$2
    x = x + h[c]*$2;
    y = y + v[c]*$2*a;
}
END {
    print x*y;
}
