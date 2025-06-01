def forward_chaining(clauses, query):
    inferred = set()
    new_symbols = True  # loop while we infer new stuff
    while new_symbols:
        new_symbols = False
        for clause in clauses:
            premises, conclusion = clause
            if all(premise in inferred for premise in premises) and conclusion not in inferred:
                if conclusion == query:
                    return True
                inferred.add(conclusion)
                new_symbols = True
    return False # no new symbols, query symbol was not seen


knowledge = [
    ([], "fragile"), #facts
    ([], "falls_down"),
    ([], "contains_liquid"),
    (["fragile", "falls_down"], "breaks"), #rules
    (["breaks", "contains_liquid"], "makes_a_mess"),
    (["spoiled", "breaks"], "smells")
]

breaks_result = forward_chaining(knowledge, "breaks")
print(f"egg breaks: {breaks_result}")
makes_a_mess_result = forward_chaining(knowledge, "makes_a_mess")
print(f"egg makes a mess: {makes_a_mess_result}")
smells_result = forward_chaining(knowledge, "smells")
print(f"egg smells: {smells_result}")

