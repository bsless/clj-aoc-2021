BEGIN {
    x=0;
    y=0;
    h["forward"]=1;
    h["up"]=0;
    h["down"]=0;
    v["forward"]=0;
    v["up"]=-1;
    v["down"]=1;
}
{
    x = x + h[$1]*$2;
    y = y + v[$1]*$2;
}
END {
    print x*y;
}
