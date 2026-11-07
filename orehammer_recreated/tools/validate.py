#!/usr/bin/env python3
"""Comprobaciones estaticas del proyecto (sin Minecraft): JSON valido, modelos/texturas/lang/items coherentes."""
import json, os, sys, glob
NS = "orehammer_recreated"
root = os.path.join(os.path.dirname(os.path.abspath(__file__)), "..")
res = os.path.join(root, "src/main/resources")
errors = []
def load(p):
    try:
        return json.load(open(p, encoding="utf-8"))
    except Exception as e:
        errors.append(f"JSON invalido {p}: {e}"); return None
for p in glob.glob(res + "/**/*.json", recursive=True): load(p)
if not os.path.exists(os.path.join(res, f"{NS}.mixins.json")): errors.append("falta mixins.json")
items = [os.path.basename(p)[:-5] for p in glob.glob(f"{res}/assets/{NS}/items/*.json")]
lang = {l: load(f"{res}/assets/{NS}/lang/{l}.json") or {} for l in ("en_us", "es_es", "es_mx")}
for n in items:
    for f in (f"assets/{NS}/models/item/{n}.json", f"assets/{NS}/textures/item/{n}.png"):
        if not os.path.exists(os.path.join(res, f)): errors.append("falta " + f)
    for l, d in lang.items():
        if f"item.{NS}.{n}" not in d: errors.append(f"falta traduccion {l}: {n}")
for p in glob.glob(res + f"/data/{NS}/recipe/**/*.json", recursive=True):
    r = load(p)
    if r and r.get("type") == f"{NS}:crushing" and not (1 <= len(r["ingredients"]) <= 2 and r["count"] >= 1):
        errors.append("receta crushing mal formada " + p)
print(f"{len(items)} objetos, {len(glob.glob(res + f'/data/{NS}/recipe/**/*.json', recursive=True))} recetas")
print("OK" if not errors else "\n".join(errors)); sys.exit(1 if errors else 0)
