import csv
import locale
import os
import itertools
from datetime import datetime


def get_path(file):
    path = os.path.dirname(__file__)
    return os.path.join(path, file)


with open(get_path('./geracao.csv'), encoding='utf-16') as csv_file:
    reader1, reader2 = itertools.tee(csv.reader(csv_file, delimiter='\t'))
    columns = len(next(reader1))
    del reader1

    geracao_csv = list(reader2)
    rows = len(geracao_csv)

    col_start = geracao_csv[1].index('1  de jan  de 19')

    locale.setlocale(locale.LC_ALL, 'pt_pt.UTF-8')

    csv_clean = []
    for col_i in range(col_start, columns):
        for row_i in range(2, rows):
            item = geracao_csv[row_i][col_i]
            if item != '\t' and item != '':
                d = datetime.strptime(geracao_csv[1][col_i].replace("  ", " "), '%d de %b de %y')
                item_clean = {
                    'data': d.strftime('%Y-%m-%d'),
                    'carga': float(item)
                }
                csv_clean.append(item_clean)
                break

    with open(get_path('./geracao_tratado.csv'), mode='w', newline='') as csv_new_file:
        fieldnames = ['data', 'carga']
        writer = csv.DictWriter(csv_new_file, delimiter=';', fieldnames=fieldnames)
        writer.writeheader()

        for item in csv_clean:
            writer.writerow(item)
