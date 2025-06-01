import random
import nltk
import tiktoken
from nltk.tokenize import sent_tokenize, word_tokenize

nltk.data.path.append('/Users/diana/nltk_data')
EOS = "<EOS>"
def tokenize_word_level(text):
    tokens = []
    for sentence in sent_tokenize(text):
        tokens.append(EOS)
        for token in word_tokenize(sentence):
            tokens.append(token)
    tokens.append(EOS)
    return tokens

def tokenize_char_level(text):
    return list(text)

enc = tiktoken.get_encoding("gpt2")
def tokenize_subword_level(text):
    return enc.encode(text)

def decode_subword(tokens):
    return enc.decode(tokens)

def create_ngrams(tokens, n):
    ngrams = {}
    for i in range(len(tokens) - n + 1):
        key = tuple(tokens[i:i + n - 1])
        next_token = tokens[i + n - 1]
        if key not in ngrams:
            ngrams[key] = {}
        if next_token not in ngrams[key]:
            ngrams[key][next_token] = 0
        ngrams[key][next_token] += 1
    return ngrams

def generate_with_backoff(ngrams, start_tokens, max_length=100):
    current_tokens = start_tokens
    result = list(current_tokens)
    for _ in range(max_length):
        for n in range(len(current_tokens), 0, -1):
            ngram_key = tuple(current_tokens[-n:])
            next_tokens = ngrams.get(ngram_key, None)
            if next_tokens:
                next_token = random.choices(
                    list(next_tokens.keys()), weights=next_tokens.values())[0]
                result.append(next_token)
                current_tokens.append(next_token)
                current_tokens = current_tokens[-n:]
                if next_token == EOS:
                    return " ".join(result)
                break
        else:
            break
    return " ".join(result)

def generate_with_backoff_subword(ngrams, start_tokens, max_length=100):
    current_tokens = start_tokens
    result = list(current_tokens)
    for _ in range(max_length):
        for n in range(len(current_tokens), 0, -1):
            ngram_key = tuple(current_tokens[-n:])
            next_tokens = ngrams.get(ngram_key, None)
            if next_tokens:
                next_token = random.choices(
                    list(next_tokens.keys()), weights=next_tokens.values())[0]
                result.append(next_token)
                current_tokens.append(next_token)
                current_tokens = current_tokens[-n:]
                if next_token == enc.encode(EOS)[0]:
                    return decode_subword(result)
                break
        else:
            break
    return decode_subword(result)

def get_random_start(tokens, n):
    start_index = random.randint(0, len(tokens) - n)
    return tokens[start_index:start_index + n - 1]

def test_all_combinations():
    print("\nWord")
    for n in [2, 3, 4]:
        ngrams = create_ngrams(word_tokens, n)
        start_tokens = get_random_start(word_tokens, n)
        print(f"\n{n}-gram:")
        print(generate_with_backoff(ngrams, start_tokens))
    print("\nCharacter")
    for n in [2, 3, 4]:
        ngrams = create_ngrams(char_tokens, n)
        start_tokens = get_random_start(char_tokens, n)
        print(f"\n{n}-gram:")
        print(generate_with_backoff(ngrams, start_tokens))
    print("\nSubword")
    for n in [2, 3, 4]:
        ngrams = create_ngrams(subword_tokens, n)
        start_tokens = get_random_start(subword_tokens, n)
        print(f"\n{n}-gram:")
        print(generate_with_backoff_subword(ngrams, start_tokens))

if __name__ == "__main__":
    with open("../../../Downloads/andersen_gutenberg2.txt", encoding="utf-8") as f:
        text = f.read()
    word_tokens = tokenize_word_level(text)
    char_tokens = tokenize_char_level(text)
    subword_tokens = tokenize_subword_level(text)
    test_all_combinations()
