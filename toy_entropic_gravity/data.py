import numpy as np
import matplotlib.pyplot as plt
from sklearn.linear_model import LinearRegression, Ridge, Lasso
from sklearn.metrics import r2_score

data = np.loadtxt('./output/230131164340.csv', delimiter=',')
train_size = round(data.shape[0] * 0.75)

x = data[:,0]
print(x)
x = np.log(1 / x)
print(x)
x = x.reshape(-1, 1)
x_train = x[:train_size]
x_test = x[train_size:]

y = data[:,1]
y = np.log(y)
y_train = y[:train_size]
y_test = y[train_size:]

fig, ax = plt.subplots()
ax.scatter(x, y)

models = LinearRegression, Ridge, Lasso
model_names = 'Linear regression', 'Ridge regression', 'Lasso regression'
colours = 'red', 'green', 'blue'

for i, model in enumerate(models):
    reg = model()
    reg.fit(x_train, y_train)

    y_pred = reg.predict(x_test)
    m = reg.coef_[0]
    c = reg.intercept_
    ax.plot(x, c + m*x, color=colours[i], linestyle='-')

    print(model_names[i] + ":")
    print('Gradient:', m)
    print('Intercept:', c)
    print('Coefficient of determination:', r2_score(y_test, y_pred))
    print('')

plt.text(1, 17, 'ln(F) = {0:.2f} + {1:.2f}ln(1/x)'.format(c, m), size=14)
plt.show()